package dev.eastar.main

import android.log.Log
import android.util.*
import dev.eastar.repository.GameRepository
import dev.eastar.usecase.GameRoundUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.Mockito


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MultiViewModelCoroutineTest {
    private lateinit var gameRoundUseCase: GameRoundUseCase

    companion object {
        @JvmField
        @RegisterExtension
        val coroutineExtension = MainCoroutineExtension()
    }

    @BeforeEach
    fun setUp() {
        Log.e("start - setUp")
        val gameRepository: GameRepository by lazy { mock() }
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        assertThat(gameRepository.generateRandomNumber(), CoreMatchers.`is`(5))
        //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
        Mockito.verify(gameRepository, Mockito.times(1)).generateRandomNumber()
        gameRoundUseCase = GameRoundUseCase(gameRepository)
    }

    @AfterEach
    fun tearDown() {
        Log.e("start - tearDown")
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가1명이면 2명이상필요하다요청한다")
    fun setMembersAsync() = coroutineExtension.runBlockingTest {
        Log.e("\tstart - setMembersAsyncTest")
        //given
        val viewModel = MultiViewModel(
            gameRoundUseCase,
            //coroutineExtension,
        )
        viewModel.members.observeForever {}
        viewModel.memberInput.observeForever {}
        viewModel.members1Player.observeForever {}

        //when
        Log.e("\tstart - viewModel.setMembersAsync(\"성춘향\")")
        viewModel.setMembersAsync("성춘향")
        Log.e("\tend - viewModel.setMembersAsync(\"성춘향\")")
        Log.e("\tstart - advanceUntilIdle")
        coroutineExtension.advanceUntilIdle()
        Log.e("\tend - advanceUntilIdle")

        //then
        assertAll({
            val actual = viewModel.members.value
            assertThat(actual, CoreMatchers.`is`(arrayOf("성춘향")))
        }, {
            val actual = viewModel.memberInput.value
            assertThat(actual, CoreMatchers.`is`(Unit))
        }, {
            val actual = viewModel.members1Player.value
            assertThat(actual, `is`("멀티 게임에서는 2명 이상의 player가 필요합니다."))
        })
        Log.e("\tend - setMembersAsyncTest")
    }


}

