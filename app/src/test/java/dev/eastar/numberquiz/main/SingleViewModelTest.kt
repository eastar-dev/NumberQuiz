package dev.eastar.numberquiz.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.eastar.numberquiz.data.GameResult
import dev.eastar.numberquiz.data.repo.GameRepository
import dev.eastar.numberquiz.main.SingleViewModel
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class SingleViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

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
            MatcherAssert.assertThat(actual, Is.`is`(gameResult))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            singleViewModel.gameResult.removeObserver(observer)
        }
    }
}

class GameRepositoryFack : GameRepository {
    override fun generateRandomNumber() =1
}
