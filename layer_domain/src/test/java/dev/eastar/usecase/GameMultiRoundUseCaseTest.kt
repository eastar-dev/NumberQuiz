package dev.eastar.usecase

import android.util.isit
import android.util.mock
import android.util.whenever
import dev.eastar.entity.GameMultiEntity
import dev.eastar.entity.RoundResultEntity
import dev.eastar.repository.GameRepository
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import kotlin.random.Random

@DisplayName("멀티게임에서 ")
internal class GameMultiRoundUseCaseTest {

    private lateinit var gameMultiRoundUseCase: GameMultiRoundUseCase

    @BeforeEach
    fun setUp() {
        val gameRepository: GameRepository by lazy { mock() }
        whenever(gameRepository.getMultiGame()).thenReturn(GameMultiEntity(5))
        assertTrue(gameRepository.getMultiGame().answer == 5)
        //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
        Mockito.verify(gameRepository, Mockito.times(1)).getMultiGame()
        gameMultiRoundUseCase = GameMultiRoundUseCase(gameRepository)
    }

    @Test
    @DisplayName("1번 호출 해서 \"성춘향\"이 이긴경우 경우")
    fun round_1() {
        //given
        val case = gameMultiRoundUseCase
        //when
        val actual = case(5, "성춘향")
        //then
        assertAll({
            MatcherAssert.assertThat(actual.roundResult, isit(RoundResultEntity.CORRECT))
        }, {
            MatcherAssert.assertThat(actual.winner, isit("성춘향"))
        }, {
            MatcherAssert.assertThat(actual.isEndGame, isit(true))
        })
    }

    @Test
    @DisplayName("n번 호출 해서 \"성춘향\"이 이긴경우 경우")
    fun round_n() {
        //given
        val case = gameMultiRoundUseCase
        val n = Random.nextInt(100)
        //when
        repeat(n - 1) {
            case(1, "변사또")
        }
        val actual = case(5, "성춘향")
        //then
        assertAll({
            MatcherAssert.assertThat(actual.roundResult, isit(RoundResultEntity.CORRECT))
        }, {
            MatcherAssert.assertThat(actual.winner, isit("성춘향"))
        }, {
            MatcherAssert.assertThat(actual.isEndGame, isit(true))
        })
    }

    @Test
    @DisplayName("n번 호출 하고 실패한 경우")
    fun round_n_fail() {
        //given
        val case = gameMultiRoundUseCase
        val n = Random.nextInt(100)
        //when
        repeat(n - 1) {
            case(1, "변사또")
        }
        val actual = case(1, "성춘향")
        //then
        assertAll({
            MatcherAssert.assertThat(actual.roundResult, isit(RoundResultEntity.LOW))
        }, {
            MatcherAssert.assertThat(actual.winner, IsNull())
        }, {
            MatcherAssert.assertThat(actual.isEndGame, isit(false))
        })
    }
}