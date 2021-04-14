package dev.eastar.numberquiz.main

import android.log.Log
import android.util.*
import androidx.lifecycle.viewModelScope
import dev.eastar.domain.TryNumberUseCase
import dev.eastar.domain.TryNumberUseCaseImpl
import dev.eastar.repository.GameRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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
//    private val testDispatcher = TestCoroutineDispatcher()
//    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var tryNumberUseCase: TryNumberUseCase

    companion object {
        @JvmField
        @RegisterExtension
        val coroutineExtension = MainCoroutineExtension()
    }

    @BeforeEach
    fun setUp() {

        Log.e()
//        Dispatchers.setMain(testDispatcher)
        Log.e()

        val gameRepository: GameRepository by lazy { mock() }
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        assertThat(gameRepository.generateRandomNumber(), CoreMatchers.`is`(5))
        //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
        Mockito.verify(gameRepository, Mockito.times(1)).generateRandomNumber()
        Log.e()
        tryNumberUseCase = TryNumberUseCaseImpl(gameRepository)
        Log.e()
    }

    @AfterEach
    fun exit() {
        Log.e()
//        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        Log.e()
//        testDispatcher.cleanupTestCoroutines()
        Log.e()
//        testScope.cleanupTestCoroutines()
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가1명이면 2명이상필요하다요청한다")
    fun setMembers_1player_case2() = coroutineExtension.runBlockingTest {
        Log.e()
        //given
        val viewModel = MultiViewModel(tryNumberUseCase)
        viewModel.members.observeForever{}
        viewModel.memberInput.observeForever{}
        viewModel.members1Player.observeForever{}
        Log.e()

        //when
        viewModel.setMembers2("성춘향")
//        delay(200)
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

}

