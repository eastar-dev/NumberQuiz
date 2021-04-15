package dev.eastar.domain

import android.util.mock
import android.util.whenever
import dev.eastar.entity.GameEntity
import dev.eastar.entity.TryResultEntity
import dev.eastar.repository.GameRepository
import dev.eastar.usecase.TryNumberUseCase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class TryNumberUseCaseTest {
    private lateinit var tryNumberUseCase: TryNumberUseCase

    @BeforeEach
    fun setUp() {
        val gameRepository: GameRepository by lazy { mock() }
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        MatcherAssert.assertThat(gameRepository.generateRandomNumber(), CoreMatchers.`is`(5))
        //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
        Mockito.verify(gameRepository, Mockito.times(1)).generateRandomNumber()

        tryNumberUseCase = TryNumberUseCase(gameRepository)
    }

    @Test
    fun tryNumber() {
        //given
        val case = tryNumberUseCase
        //when
        case.setPlayers(arrayOf("성춘향", "변사또"))
        val actual = case.tryNumber(5)
        //then
        assertAll({
            MatcherAssert.assertThat(actual.tryResult, CoreMatchers.`is`(TryResultEntity.correct))
        }, {
            MatcherAssert.assertThat(actual.winner, CoreMatchers.`is`("성춘향"))
        })
    }

    @Test
    fun tryNumber5() {
        //given
        val case = tryNumberUseCase
        //when
        case.setPlayers(arrayOf("성춘향", "변사또"))

        //then

        assertAll(
            { MatcherAssert.assertThat(case.tryNumber(0).tryResult, CoreMatchers.`is`(TryResultEntity.low)) },
            { MatcherAssert.assertThat(case.tryNumber(9).tryResult, CoreMatchers.`is`(TryResultEntity.high)) },
            { MatcherAssert.assertThat(case.tryNumber(1).tryResult, CoreMatchers.`is`(TryResultEntity.low)) },
            { MatcherAssert.assertThat(case.tryNumber(8).tryResult, CoreMatchers.`is`(TryResultEntity.high)) },
            { MatcherAssert.assertThat(case.tryNumber(2).tryResult, CoreMatchers.`is`(TryResultEntity.low)) },
            { MatcherAssert.assertThat(case.tryNumber(7).tryResult, CoreMatchers.`is`(TryResultEntity.high)) },
            { MatcherAssert.assertThat(case.tryNumber(4).tryResult, CoreMatchers.`is`(TryResultEntity.low)) },
            { MatcherAssert.assertThat(case.tryNumber(6).tryResult, CoreMatchers.`is`(TryResultEntity.high)) },
            { MatcherAssert.assertThat(case.tryNumber(4).tryResult, CoreMatchers.`is`(TryResultEntity.low)) },
            {
                val actual: GameEntity = case.tryNumber(5)
                MatcherAssert.assertThat(actual.tryResult, CoreMatchers.`is`(TryResultEntity.correct))
                MatcherAssert.assertThat(actual.winner, CoreMatchers.`is`("변사또"))
            }
        )
    }
}