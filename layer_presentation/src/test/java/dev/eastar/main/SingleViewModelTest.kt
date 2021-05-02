package dev.eastar.main

import dev.eastar.entity.GameEntity
import dev.eastar.entity.RoundResult
import dev.eastar.usecase.GameRoundUseCase
import dev.eastar.usecase.GameStartUseCase
import junit.util.InstantExecutorExtension
import junit.util.getOrAwaitValue
import junit.util.mock
import junit.util.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.verify


@DisplayName("싱글게임에서 ")
@ExtendWith(InstantExecutorExtension::class)
class SingleViewModelTest {
    private val gameStartUseCase: GameStartUseCase by lazy { mock() }
    private val gameRoundUseCase: GameRoundUseCase by lazy { mock() }


    @Test
    @DisplayName("시작시 gameStartUseCase()가 호출된다.")
    fun initTest() {
        //given
        SingleViewModel(gameStartUseCase, gameRoundUseCase)

        //when
        whenever(gameStartUseCase.invoke()).thenAnswer {}

        //then
        verify(gameStartUseCase, Mockito.times(1)).invoke()
    }

    //https://greedy0110.tistory.com/57
    @Test
    @DisplayName("round LOW_ANSWER면 roundResult:LOW")
    fun round_LOW_ANSWER_Test() {
        //given
        val viewModel = SingleViewModel(gameStartUseCase, gameRoundUseCase)
        whenever(gameRoundUseCase.invoke(LOW_ANSWER)).thenReturn(GameEntity(LOW_ANSWER).apply {
            roundResult = RoundResult.LOW
        })

        //when
        viewModel.guess.value = "" + LOW_ANSWER
        viewModel.round()

        //then
        //assertAll({
        val actual = viewModel.roundResult.getOrAwaitValue()
        assertThat(actual, `is`(RoundResult.LOW))
        //}, {
        //    val actual = viewModel.isEndGame.getOrAwaitValue()
        //    assertThat(actual, `is`(nullValue()))
        //})
    }

    @Test
    @DisplayName("round HIGH_ANSWER roundResult:HIGH")
    fun round_HIGH_ANSWER_Test() {
        //given
        val viewModel = SingleViewModel(gameStartUseCase, gameRoundUseCase)
        whenever(gameRoundUseCase.invoke(HIGH_ANSWER)).thenReturn(GameEntity(HIGH_ANSWER).apply {
            roundResult = RoundResult.HIGH
        })

        //when
        viewModel.guess.value = "" + HIGH_ANSWER
        viewModel.round()

        //then
        //assertAll({
        val actual = viewModel.roundResult.getOrAwaitValue()
        assertThat(actual, `is`(RoundResult.HIGH))
        //}, {
        //    val actual = viewModel.isEndGame.getOrAwaitValue()
        //    assertThat(actual, `is`(nullValue()))
        //})
    }

    @Test
    @DisplayName("round CORRECT_ANSWER roundResult:CORRECT and ")
    fun round_CORRECT_Test() {
        //given
        val viewModel = SingleViewModel(gameStartUseCase, gameRoundUseCase)
        whenever(gameRoundUseCase.invoke(CORRECT_ANSWER)).thenReturn(GameEntity(CORRECT_ANSWER).apply {
            roundResult = RoundResult.CORRECT
            isEndGame = true
            roundCount = 1
        })

        //when
        viewModel.guess.value = "" + CORRECT_ANSWER
        viewModel.round()

        //then
        assertAll({
            val actual = viewModel.roundResult.getOrAwaitValue()
            assertThat(actual, `is`(RoundResult.CORRECT))
        }, {
            val actual = viewModel.endGameMsg.getOrAwaitValue()
            assertThat(actual, `is`("축하합니다. 총시도 횟수는 1번 입니다."))
        })
    }
}