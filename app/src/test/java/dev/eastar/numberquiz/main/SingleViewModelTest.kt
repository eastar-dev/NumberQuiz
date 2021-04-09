package dev.eastar.numberquiz.main

import android.util.mock
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.eastar.numberquiz.InstantExecutorExtension
import dev.eastar.numberquiz.data.GameResult
import dev.eastar.numberquiz.data.repo.GameRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantExecutorExtension::class)
class SingleViewModelTest {
    @BeforeEach
    fun init() {
        android.log.Log.outputSystem()
    }


    //https://greedy0110.tistory.com/57
    @Test
    fun tryNumber() {
        //given
        val gameRepository: GameRepository = mock()
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)

        val singleViewModel = SingleViewModel(gameRepository)
        verify(gameRepository, times(1))
//        MatcherAssert.assertThat(gameRepository.generateRandomNumber(), `is`(5))

        //when
        singleViewModel.gameResult.observeForever { }
        singleViewModel.gameEnd.observeForever {}
        singleViewModel.tryingNumber.value = "1"
        singleViewModel.tryNumber()

        //then
        assertAll({
            val actual = singleViewModel.gameResult.value
            MatcherAssert.assertThat(actual, `is`(GameResult.low))
        }, {
            val actual = singleViewModel.gameEnd.value
            MatcherAssert.assertThat(actual, `is`(nullValue()))
        })
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

    @Test
    fun tryNumber_correct2() {
        //given
        val singleViewModel = SingleViewModel(GameRepositoryFack())
        //when
        val observer = Observer<String> {}
        singleViewModel.gameEnd.observeForever(observer)
        arrayOf("1", "5").forEach {
            singleViewModel.tryingNumber.value = it
            singleViewModel.tryNumber()
        }

        try {
            //then
            val actual = singleViewModel.gameEnd.value
            MatcherAssert.assertThat(actual, `is`("축하합니다. 총시도 횟수는 2번 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            singleViewModel.gameEnd.removeObserver(observer)
        }
    }

    @Test
    fun tryNumber_correct3() {
        //given
        val singleViewModel = SingleViewModel(GameRepositoryFack())
        //when
        val observer = Observer<String> {}
        singleViewModel.gameEnd.observeForever(observer)
        arrayOf("1", "2", "5").forEach {
            singleViewModel.tryingNumber.value = it
            singleViewModel.tryNumber()
        }

        try {
            //then
            val actual = singleViewModel.gameEnd.value
            MatcherAssert.assertThat(actual, `is`("축하합니다. 총시도 횟수는 3번 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            singleViewModel.gameEnd.removeObserver(observer)
        }
    }
}