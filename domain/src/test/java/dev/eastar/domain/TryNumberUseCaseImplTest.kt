package dev.eastar.domain

import android.util.mock
import android.util.whenever
import dev.eastar.enty.GameResult
import dev.eastar.repository.GameRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class TryNumberUseCaseImplTest {
    private lateinit var tryNumberUseCase: TryNumberUseCase

    @BeforeEach
    fun setUp() {
        val gameRepository: GameRepository by lazy { mock() }
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        MatcherAssert.assertThat(gameRepository.generateRandomNumber(), CoreMatchers.`is`(5))
        //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
        Mockito.verify(gameRepository, Mockito.times(1)).generateRandomNumber()

        tryNumberUseCase = TryNumberUseCaseImpl(gameRepository)
    }

    @Test
    fun tryNumber() {
        //given
        val case = tryNumberUseCase
        //when
        case.player = arrayOf("성춘향", "변사또")
        val actual = case.tryNumber(5)
        //then
        assertAll({
            MatcherAssert.assertThat(actual, CoreMatchers.`is`(GameResult.correct))
        }, {
            MatcherAssert.assertThat(case.winner, CoreMatchers.`is`("성춘향"))
        })
    }

    @Test
    fun tryNumber5() {
        //given
        val case = tryNumberUseCase
        //when
        case.player = arrayOf("성춘향", "변사또")

        //then
        assertAll(
            { MatcherAssert.assertThat(case.tryNumber(0), CoreMatchers.`is`(GameResult.low)) },
            { MatcherAssert.assertThat(case.tryNumber(9), CoreMatchers.`is`(GameResult.high)) },
            { MatcherAssert.assertThat(case.tryNumber(1), CoreMatchers.`is`(GameResult.low)) },
            { MatcherAssert.assertThat(case.tryNumber(8), CoreMatchers.`is`(GameResult.high)) },
            { MatcherAssert.assertThat(case.tryNumber(2), CoreMatchers.`is`(GameResult.low)) },
            { MatcherAssert.assertThat(case.tryNumber(7), CoreMatchers.`is`(GameResult.high)) },
            { MatcherAssert.assertThat(case.tryNumber(4), CoreMatchers.`is`(GameResult.low)) },
            { MatcherAssert.assertThat(case.tryNumber(6), CoreMatchers.`is`(GameResult.high)) },
            { MatcherAssert.assertThat(case.tryNumber(4), CoreMatchers.`is`(GameResult.low)) },
            { MatcherAssert.assertThat(case.tryNumber(5), CoreMatchers.`is`(GameResult.correct)) },
            { MatcherAssert.assertThat(case.winner, CoreMatchers.`is`("성춘향")) },
        )
    }
}