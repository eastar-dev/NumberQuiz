package dev.eastar.usecase

import dev.eastar.entity.GameEntity
import dev.eastar.repository.GameRepository
import junit.util.isit
import junit.util.mock
import junit.util.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.random.Random

@DisplayName("게임Round진행시 ")
internal class GameRoundUseCaseTest {

    private lateinit var gameRoundUseCase: GameRoundUseCase
    private val gameRepository: GameRepository by lazy { mock() }

    @BeforeEach
    fun setUp() {
        whenever(gameRepository.getGame()).thenReturn(GameEntity(CORRECT_ANSWER))
        gameRoundUseCase = GameRoundUseCase(gameRepository, RoundResultUseCase())
    }

    @Test
    @DisplayName("1번에 guess가정답이면 isEndGame=true roundCount=1로 바꾼다.")
    fun invoke() {
        //given
        val case = gameRoundUseCase

        //when
        val actual = case(CORRECT_ANSWER)

        //then
        assertAll({
            assertThat(actual.isEndGame, isit(true))
        }, {
            assertThat(actual.roundCount, isit(1))
        })
    }

    @Test
    @DisplayName("1번에 guess가오답이면 isEndGame=false roundCount=1로 바꾼다.")
    fun invoke_false() {
        //given
        val case = gameRoundUseCase

        //when
        val actual = case(WARN_ANSWER)

        //then
        assertAll({
            assertThat(actual.isEndGame, isit(false))
        }, {
            assertThat(actual.roundCount, isit(1))
        })
    }

    @Test
    @DisplayName("n번에 guess가정답이면 isEndGame=true roundCount=n로 바꾼다.")
    fun invoke_n() {
        //given
        val case = gameRoundUseCase

        //when
        val n = Random.nextInt(1, 101)
        repeat(n - 1) {
            case(WARN_ANSWER)
        }
        val actual = case(CORRECT_ANSWER)

        //then
        assertAll({
            assertThat(actual.isEndGame, isit(true))
        }, {
            assertThat(actual.roundCount, isit(n))
        })
    }
}