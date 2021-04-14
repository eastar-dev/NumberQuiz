package dev.eastar.numberquiz.main

import android.util.InstantExecutorExtension
import android.util.whenever
import androidx.lifecycle.Observer
import dev.eastar.domain.TryNumberUseCase
import dev.eastar.domain.TryNumberUseCaseImpl
import dev.eastar.enty.GameResult
import dev.eastar.repository.GameRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.Mockito.times
import org.mockito.Mockito.verify


@ExtendWith(InstantExecutorExtension::class)
class SingleViewModelTest {
//    private val gameRepositoryMock: GameRepository by lazy { mock(GameRepository::class.java) }
//    private val gameRepositorySpy: GameRepository by lazy { spy(GameRepository::class.java) }

    private lateinit var tryNumberUseCase: TryNumberUseCase

    @BeforeEach
    fun init() {
        val gameRepository: GameRepository by lazy { android.util.mock() }
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        assertThat(gameRepository.generateRandomNumber(), `is`(5))
        //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
        verify(gameRepository, times(1)).generateRandomNumber()

        tryNumberUseCase = TryNumberUseCaseImpl(gameRepository)
    }


    //https://greedy0110.tistory.com/57
    @Test
    fun tryNumber() {
        //given
        val viewModel = SingleViewModel(tryNumberUseCase)

        //when
        viewModel.gameResult.observeForever { }
        viewModel.gameEnd.observeForever {}
        viewModel.tryingNumber.value = "2"
        viewModel.tryNumber()

        //then
        assertAll({
            val actual = viewModel.gameResult.value
            assertThat(actual, `is`(GameResult.low))
        }, {
            val actual = viewModel.gameEnd.value
            assertThat(actual, `is`(nullValue()))
        })
    }

    @ParameterizedTest
    @CsvSource(value = ["1,0", "3,0", "5,1", "7,2", "9,2"])
    fun tryNumber(number: String, result: Int) {
        //given
        val viewModel = SingleViewModel(tryNumberUseCase)
        //when
        val observer = Observer<GameResult> {}
        viewModel.gameResult.observeForever(observer)
        viewModel.tryingNumber.value = number
        viewModel.tryNumber()

        try {
            //then
            val gameResult = GameResult.values()[result]
            val actual = viewModel.gameResult.value
            assertThat(actual, `is`(gameResult))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameResult.removeObserver(observer)
        }
    }

    //    @ValueSource(ints = [1,3,5,7,9])
//    @ParameterizedTest
//    @CsvSource(value = ["1,-1", "3,-1", "5,0", "7,+1", "9,+1"])
//    fun signmunTest(number: Int, result: Int) {
//        //given
//        val viewModel = SingleViewModel(tryNumberUseCase)
//        //when
//        val actual = signumTest(number)
//        //then
//        assertThat(actual, `is`(result))
//
//    }

    @Test
    fun tryNumber_correct() {
        //given
        val viewModel = SingleViewModel(tryNumberUseCase)
        //when
        val observer = Observer<String> {}
        viewModel.gameEnd.observeForever(observer)
        viewModel.tryingNumber.value = "5"
        viewModel.tryNumber()

        try {
            //then
            val actual = viewModel.gameEnd.value
            assertThat(actual, `is`("축하합니다. 총시도 횟수는 1번 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameEnd.removeObserver(observer)
        }
    }

    @Test
    fun tryNumber_correct2() {
        //given
        val viewModel = SingleViewModel(tryNumberUseCase)
        //when
        val observer = Observer<String> {}
        viewModel.gameEnd.observeForever(observer)
        arrayOf("1", "5").forEach {
            viewModel.tryingNumber.value = it
            viewModel.tryNumber()
        }

        try {
            //then
            val actual = viewModel.gameEnd.value
            assertThat(actual, `is`("축하합니다. 총시도 횟수는 2번 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameEnd.removeObserver(observer)
        }
    }

    @Test
    fun tryNumber_correct3() {
        //given
        val viewModel = SingleViewModel(tryNumberUseCase)
        //when
        val observer = Observer<String> {}
        viewModel.gameEnd.observeForever(observer)
        arrayOf("1", "2", "5").forEach {
            viewModel.tryingNumber.value = it
            viewModel.tryNumber()
        }

        try {
            //then
            val actual = viewModel.gameEnd.value
            assertThat(actual, `is`("축하합니다. 총시도 횟수는 3번 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameEnd.removeObserver(observer)
        }
    }
}