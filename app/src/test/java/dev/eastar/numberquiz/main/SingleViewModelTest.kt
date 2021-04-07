package dev.eastar.numberquiz.main

import androidx.lifecycle.Observer
import dev.eastar.numberquiz.InstantExecutorExtension
import dev.eastar.numberquiz.data.GameResult
import dev.eastar.numberquiz.data.repo.GameRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantExecutorExtension::class)
class SingleViewModelTest {
    @BeforeEach
    fun init() {
        android.log.Log.outputSystem()
    }

    @ParameterizedTest
    @CsvSource(value = ["1,0", "3,0", "5,1", "7,2", "9,2"])
    fun tryNumber(number: String, result: Int) {
        //given
        val singleViewModel = SingleViewModel(GameRepositoryFack())
        //when
        val observer = Observer<GameResult> {}
        singleViewModel.gameResult.observeForever(observer)
        singleViewModel.tryingNumber.value = number
        singleViewModel.tryNumber()

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

    //    @ValueSource(ints = [1,3,5,7,9])
    @ParameterizedTest
    @CsvSource(value = ["1,-1", "3,-1", "5,0", "7,+1", "9,+1"])
    fun signmunTest(number: Int, result: Int) {
        //given
        val singleViewModel = SingleViewModel(GameRepositoryFack())
        //when
        val actual = singleViewModel.signumTest(number)
        //then
        MatcherAssert.assertThat(actual, `is`(result))

    }

    @Test
    fun tryNumber_correct() {
        //given
        val singleViewModel = SingleViewModel(GameRepositoryFack())
        //when
        val observer = Observer<String> {}
        singleViewModel.gameEnd.observeForever(observer)
        singleViewModel.tryingNumber.value = "5"
        singleViewModel.tryNumber()

        try {
            //then
            val actual = singleViewModel.gameEnd.value
            MatcherAssert.assertThat(actual, `is`("축하합니다. 총시도 횟수는 1번 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            singleViewModel.gameEnd.removeObserver(observer)
        }
    }
}

class GameRepositoryFack : GameRepository {
    override fun generateRandomNumber() = 5
}
