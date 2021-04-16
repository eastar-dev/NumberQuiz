package dev.eastar.main

import android.log.Log
import android.util.InstantExecutorExtension
import android.util.getOrAwaitValue
import android.util.mock
import android.util.whenever
import dev.eastar.repository.GameRepository
import dev.eastar.usecase.TryNumberUseCase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import java.util.*


@ExtendWith(InstantExecutorExtension::class)
class MultiViewModelExecutorsTest {
    private lateinit var tryNumberUseCase: TryNumberUseCase

    @BeforeEach
    fun setUp() {
        Log.e()
        val gameRepository: GameRepository by lazy { mock() }
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        assertThat(gameRepository.generateRandomNumber(), CoreMatchers.`is`(5))
        //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
        Mockito.verify(gameRepository, Mockito.times(1)).generateRandomNumber()

        tryNumberUseCase = TryNumberUseCase(gameRepository)
    }

    @AfterEach
    fun tearDown() {
        Log.e("end - tearDown")
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가1명이면 2명이상필요하다요청한다")
    fun setMembersExecutors() {
        Log.e()
        //given
        val viewModel = MultiViewModel(
            tryNumberUseCase,
        )
        viewModel.members.observeForever {}
        viewModel.memberInput.observeForever {}
        viewModel.members1Player.observeForever {}
        Log.e()

        //when
        viewModel.setMembersExecutors("성춘향")
        //delay(2)
        Log.e()

//        viewModel.viewModelScope.
        //then
        assertAll({
            Log.e()
            val actual = viewModel.members.value
            Log.e()
            assertThat(actual, CoreMatchers.`is`(arrayOf("성춘향")))
            Log.e()
        }, {
            Log.e()
            val actual = viewModel.memberInput.value
            Log.e()
            assertThat(actual, CoreMatchers.`is`(Unit))
            Log.e()
        }, {
            Log.e()
            val actual = viewModel.members1Player.value
            Log.e()
            assertThat(actual, `is`("멀티 게임에서는 2명 이상의 player가 필요합니다."))
            Log.e()
        })
        Log.e()
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가1명이면 2명이상필요하다요청한다")
    fun setMembersAsyncAwait() {
        //given
        val viewModel = MultiViewModel(tryNumberUseCase)

        //when
        viewModel.setMembersExecutors("성춘향")

        //then
        Log.e("getOrAwaitValue")
        Thread.sleep(0)
        val actual = viewModel.members.getOrAwaitValue()
        Log.e("getOrAwaitValue", Arrays.toString(actual))
        assertThat(actual, CoreMatchers.`is`(arrayOf("성춘향")))
        Log.e()
    }

}

