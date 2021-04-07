package dev.eastar.numberquiz.main

import androidx.lifecycle.Observer
import dev.eastar.numberquiz.data.GameResult
import dev.eastar.numberquiz.data.repo.GameRepository
import dev.eastar.tdd.InstantExecutorExtension
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class SingleViewModelTest {

    @Test
    fun tryNumber() {
        //given
        val singleViewModel = SingleViewModel(GameRepositoryFack())
        //when
        val observer = Observer<GameResult> {}
        singleViewModel.tryNumber(10, "")
        singleViewModel.gameResult.observeForever(observer)

        try {
            //then
            val gameResult = GameResult()
            val actual = singleViewModel.gameResult.value
            MatcherAssert.assertThat(actual, `is`(gameResult))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            singleViewModel.gameResult.removeObserver(observer)
        }
    }
}

class GameRepositoryFack : GameRepository {
    override fun generateRandomNumber() = 1
}
