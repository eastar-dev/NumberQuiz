package dev.eastar.numberquiz.main

import androidx.lifecycle.Observer
import dev.eastar.numberquiz.InstantExecutorExtension
import dev.eastar.numberquiz.data.GameResult
import dev.eastar.numberquiz.data.repo.GameRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantExecutorExtension::class)
class SingleViewModelTest {

    @ParameterizedTest
    @CsvSource(value = ["1,0", "2,0"])
    fun tryNumber(number: Int, result: Int) {
        //given
        val singleViewModel = SingleViewModel(GameRepositoryFack())
        //when
        val observer = Observer<GameResult> {}
        singleViewModel.gameResult.observeForever(observer)
        singleViewModel.tryNumber(number)

        try {
            //then
            val gameResult = GameResult.values()[result]
            val actual = singleViewModel.gameResult.value
            MatcherAssert.assertThat(actual, `is`(gameResult))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            singleViewModel.gameResult.removeObserver(observer)
        }
    }

    @Test
    fun signmunTest() {
        //given
        val singleViewModel = SingleViewModel(GameRepositoryFack())
        //when
        val actual = singleViewModel.signumTest(3)
        //then
        MatcherAssert.assertThat(actual, `is`(-1))

    }
}

class GameRepositoryFack : GameRepository {
    override fun generateRandomNumber() = 5
}
