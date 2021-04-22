package dev.eastar.main

import android.log.Log
import android.util.InstantExecutorExtension
import android.util.mock
import android.util.whenever
import dev.eastar.repository.GameRepository
import dev.eastar.usecase.GameRoundUseCase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import java.util.*


@ExtendWith(InstantExecutorExtension::class)
class MultiViewModelExecutorsTest {
    private lateinit var gameRoundUseCase: GameRoundUseCase

    @BeforeEach
    fun setUp() {
        val gameRepository: GameRepository by lazy { mock() }
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        assertThat(gameRepository.generateRandomNumber(), CoreMatchers.`is`(5))
        //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
        Mockito.verify(gameRepository, Mockito.times(1)).generateRandomNumber()

        gameRoundUseCase = GameRoundUseCase(gameRepository)
    }

    @AfterEach
    fun tearDown() {
        Log.e("end - tearDown")
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가1명이면 2명이상필요하다요청한다")
    fun setMembersAsyncAwait()  {
        //given
        val viewModel = MultiViewModel(gameRoundUseCase)

        //when
        Log.e("\tgetOrAwaitValue")
        viewModel.testSetMembersExecutorsRunner("성춘향")
        viewModel.members.observeForever{}

        //then
        Log.e("\tgetOrAwaitValue")
        val actual = viewModel.members.value
        Log.e("\tgetOrAwaitValue", Arrays.toString(actual))
        assertThat(actual, CoreMatchers.`is`(arrayOf("성춘향")))
    }

}

