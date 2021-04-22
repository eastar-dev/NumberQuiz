package dev.eastar.usecase

import android.util.isit
import android.util.mock
import android.util.whenever
import dev.eastar.entity.GameEntity
import dev.eastar.entity.RoundResult
import dev.eastar.repository.GameRepository
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import kotlin.random.Random

@DisplayName("싱글게임에서 ")
internal class GameSingleRoundUseCaseTest {
    //private lateinit var gameRoundUseCase: GameRoundUseCase
    //
    //@BeforeEach
    //fun setUp() {
    //    val gameRepository: GameRepository by lazy { mock() }
    //    whenever(gameRepository.getGame()).thenReturn(GameEntity(5))
    //    assertTrue(gameRepository.getGame().answer == 5)
    //    //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
    //    Mockito.verify(gameRepository, Mockito.times(1)).getGame()
    //    gameRoundUseCase = GameRoundUseCase(gameRepository)
    //}
    //
    //@Test
    //@DisplayName("1번 호출 해서 성공한 경우")
    //fun round_1() {
    //    //given
    //    val case = gameRoundUseCase
    //    //when
    //    val actual = case(5)
    //    //then
    //    assertAll({
    //        MatcherAssert.assertThat(actual.roundResult, isit(RoundResult.CORRECT))
    //    }, {
    //        MatcherAssert.assertThat(actual.roundCount, isit(1))
    //    }, {
    //        MatcherAssert.assertThat(actual.isEndGame, isit(true))
    //    })
    //}
    //
    //@Test
    //@DisplayName("n번 호출 해서 성공한 경우")
    //fun round_n() {
    //    //given
    //    val case = gameRoundUseCase
    //    val n = Random.nextInt(100)
    //    //when
    //    repeat(n - 1) {
    //        case(1)
    //    }
    //    val actual = case(5)
    //    //then
    //    assertAll({
    //        MatcherAssert.assertThat(actual.roundResult, isit(RoundResult.CORRECT))
    //    }, {
    //        MatcherAssert.assertThat(actual.roundCount, isit(n))
    //    }, {
    //        MatcherAssert.assertThat(actual.isEndGame, isit(true))
    //    })
    //}
    //
    //@Test
    //@DisplayName("n번 호출 하고 실패한 경우")
    //fun round_n_fail() {
    //    //given
    //    val case = gameRoundUseCase
    //    val n = Random.nextInt(100)
    //    //when
    //    repeat(n - 1) {
    //        case(1)
    //    }
    //    val actual = case(1)
    //    //then
    //    assertAll({
    //        MatcherAssert.assertThat(actual.roundResult, isit(RoundResult.LOW))
    //    }, {
    //        MatcherAssert.assertThat(actual.roundCount, isit(n))
    //    }, {
    //        MatcherAssert.assertThat(actual.isEndGame, isit(false))
    //    })
    //}
}