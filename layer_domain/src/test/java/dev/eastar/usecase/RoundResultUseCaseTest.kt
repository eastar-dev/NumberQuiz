package dev.eastar.usecase

import junit.util.isit
import dev.eastar.entity.GameEntity
import dev.eastar.entity.RoundResult
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import kotlin.random.Random

@DisplayName("게임Round에서 ")
internal class RoundResultUseCaseTest {

    @Test
    @DisplayName("Answer_낮은값 RoundResult.LOW")
    fun invoke_low() {
        //given
        val game = GameEntity(ANSWER)
        val case = RoundResultUseCase()
        //when
        case(game, LOW_ANSWER)
        val actual = game.roundResult
        //then
        assertThat(actual, isit(RoundResult.LOW))
    }

    @Test
    @DisplayName("Answer_높은값 RoundResult.HIGH")
    fun invoke_high() {
        //given
        val game = GameEntity(ANSWER)
        val case = RoundResultUseCase()
        //when
        case(game, HIGH_ANSWER)
        val actual = game.roundResult
        //then
        assertThat(actual, isit(RoundResult.HIGH))
    }

    @Test
    @DisplayName("Answer_정답을_넣으면 RoundResult.CORRECT")
    fun invoke_correct() {
        //given
        val game = GameEntity(ANSWER)
        val case = RoundResultUseCase()
        //when
        case(game, CORRECT_ANSWER)
        val actual = game.roundResult
        //then
        assertThat(actual, isit(RoundResult.CORRECT))
    }

    @RepeatedTest(10)
    @DisplayName("Answer_Random_정답을_넣으면 RoundResult.CORRECT")
    fun invoke_correct_random() {
        //given
        val random = Random.nextInt(1, 101)
        val game = GameEntity(random)
        val case = RoundResultUseCase()
        //when
        case(game, random)
        val actual = game.roundResult
        //then
        assertThat(actual, isit(RoundResult.CORRECT))
    }
}

