package dev.eastar.numberquiz.main

import androidx.lifecycle.Observer
import dev.eastar.numberquiz.InstantExecutorExtension
import dev.eastar.numberquiz.data.GameResult
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantExecutorExtension::class)
class MultiViewModelTest {
    @BeforeEach
    fun init() {
        android.log.Log.outputSystem()
    }

    @Test
    fun tryNumber() {
        //given
        val viewModel = MultiViewModel(GameRepositoryFack())
        //when
        val observer = Observer<GameResult> {}
        viewModel.gameResult.observeForever(observer)
        viewModel.tryingNumber.value = "1"
        viewModel.tryNumber()

        try {
            //then
            val gameResult = GameResult.low
            val actual = viewModel.gameResult.value
            val actual2 = viewModel.gameEnd.value
            MatcherAssert.assertThat(actual, CoreMatchers.`is`(gameResult))
            MatcherAssert.assertThat(actual2, CoreMatchers.`is`(Matchers.nullValue()))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameResult.removeObserver(observer)
        }
    }

    @ParameterizedTest
    @CsvSource(value = ["1,0", "3,0", "5,1", "7,2", "9,2"])
    fun tryNumber(number: String, result: Int) {
        //given
        val viewModel = MultiViewModel(GameRepositoryFack())
        //when
        val observer = Observer<GameResult> {}
        viewModel.gameResult.observeForever(observer)
        viewModel.tryingNumber.value = number
        viewModel.tryNumber()

        try {
            //then
            val gameResult = GameResult.values()[result]
            val actual = viewModel.gameResult.value
            MatcherAssert.assertThat(actual, CoreMatchers.`is`(gameResult))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameResult.removeObserver(observer)
        }
    }

    //    @ValueSource(ints = [1,3,5,7,9])
    @ParameterizedTest
    @CsvSource(value = ["1,-1", "3,-1", "5,0", "7,+1", "9,+1"])
    fun signmunTest(number: Int, result: Int) {
        //given
        val viewModel = MultiViewModel(GameRepositoryFack())
        //when
        val actual = viewModel.signumTest(number)
        //then
        MatcherAssert.assertThat(actual, CoreMatchers.`is`(result))

    }

    @Test
    fun tryNumber_correct() {
        //given
        val viewModel = MultiViewModel(GameRepositoryFack())
        //when
        val observer = Observer<String> {}
        viewModel.gameEnd.observeForever(observer)
        viewModel.members.value = arrayOf("성춘향", "변사또")

        arrayOf("5").forEach {
            viewModel.tryingNumber.value = it
            viewModel.tryNumber()
        }
        try {
            //then
            val actual = viewModel.gameEnd.value
            MatcherAssert.assertThat(actual, CoreMatchers.`is`("축하합니다.\n승자는 변사또 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameEnd.removeObserver(observer)
        }
    }

    @Test
    fun tryNumber_correct2() {
        //given
        val viewModel = MultiViewModel(GameRepositoryFack())
        //when
        val observer = Observer<String> {}
        viewModel.gameEnd.observeForever(observer)
        viewModel.members.value = arrayOf("성춘향", "변사또")

        arrayOf("1", "5").forEach {
            viewModel.tryingNumber.value = it
            viewModel.tryNumber()
        }
        try {
            //then
            val actual = viewModel.gameEnd.value
            MatcherAssert.assertThat(actual, CoreMatchers.`is`("축하합니다.\n승자는 성춘향 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameEnd.removeObserver(observer)
        }
    }
}

