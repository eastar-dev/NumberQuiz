package dev.eastar.main

import androidx.lifecycle.Observer
import dev.eastar.entity.RoundResult
import dev.eastar.repository.GameRepository
import dev.eastar.usecase.MultiGameRoundUseCase
import dev.eastar.usecase.MultiGameStartUseCase
import junit.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull.nullValue
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MultiViewModelTest {
    private lateinit var multiGameRoundUseCase: MultiGameRoundUseCase
    private lateinit var multiGameStartUseCase: MultiGameStartUseCase
    private lateinit var multiViewModel: MultiViewModel

    @BeforeEach
    fun setUp() {
        val gameRepository: GameRepository by lazy { mock() }
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        assertThat(gameRepository.generateRandomNumber(), isit(5))
        //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
        Mockito.verify(gameRepository, Mockito.times(1)).generateRandomNumber()
        multiViewModel = MultiViewModel(multiGameStartUseCase, multiGameRoundUseCase)
    }

    @Test
    fun tryNumber() {
        //given
        val viewModel = multiViewModel

        //when
        viewModel.guess.value = "1"
        viewModel.tryNumber()

        //then
        assertAll({
            val actual = viewModel.roundResult.getOrAwaitValue()
            assertThat(actual, isit(RoundResult.LOW))
        }, {
            val actual = viewModel.gameEnd.getOrAwaitValue()
            assertThat(actual, nullValue())
        })
    }

    @ParameterizedTest
    @CsvSource(value = ["1,0", "3,0", "5,1", "7,2", "9,2"])
    fun tryNumber(number: String, result: Int) {
        //given
        val viewModel = multiViewModel
        //when
        viewModel.guess.value = number
        viewModel.tryNumber()

        //then
        val roundResult = RoundResult.values()[result]
        val actual = viewModel.roundResult.getOrAwaitValue()
        assertThat(actual, isit(roundResult))
    }

    @Test
    fun tryNumber_correct() {
        //given
        val viewModel = multiViewModel
        //when
        val observer = Observer<String> {}
        viewModel.gameEnd.observeForever(observer)
        viewModel.setMembers("성춘향,변사또")

        arrayOf("5").forEach {
            viewModel.guess.value = it
            viewModel.tryNumber()
        }

        //then
        val actual = viewModel.gameEnd.getOrAwaitValue()
        assertThat(actual, isit("축하합니다.\n승자는 성춘향 입니다."))
    }

    @Test
    fun tryNumber_correct2() {
        //given
        val viewModel = multiViewModel
        //when
        val observer = Observer<String> {}
        viewModel.gameEnd.observeForever(observer)
        viewModel.setMembers("성춘향,변사또")

        arrayOf("1", "5").forEach {
            viewModel.guess.value = it
            viewModel.tryNumber()
        }
        //then
        val actual = viewModel.gameEnd.getOrAwaitValue()
        assertThat(actual, isit("축하합니다.\n승자는 변사또 입니다."))

    }

    @ExperimentalCoroutinesApi
    @ParameterizedTest
    @EmptySource //"" test를 자동으로 해준다.
    @ValueSource(strings = [" ", ",", "  , , , ,", "\t   , , , ,"])
    @DisplayName("""Multi에서 입력받은유저가""면 요청한다""")
    fun setMembers_empty(input: String) {
        //given
        val viewModel = multiViewModel

        //when
        viewModel.setMembers(input)

        //then
        assertAll({
            val actual = viewModel.memberInput.getOrAwaitValue()
            assertThat(actual, isit(Unit))
        }, {
            val actual = viewModel.members.getOrAwaitValue()
            assertThat(actual, isit(emptyArray()))
        })

    }

    @Test
    @DisplayName("Multi에서 시작시emptyplayer 요청")
    fun setMembers_empty() {
        //given
        val viewModel = multiViewModel
        //when
        //empty

        //then
        val actual = viewModel.memberInput.getOrAwaitValue()
        assertThat(actual, isit(Unit))
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가>2면 입력요청안한다")
    fun setMembers_over2_member() {
        //given
        val viewModel = multiViewModel
        //when
        viewModel.setMembers("성춘향,변사또")

        //then
        val actual = viewModel.memberInput.getOrAwaitValue()
        Assertions.assertNull(actual)
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가1명이면 2명이상필요하다요청한다")
    fun setMembers_1player() {
        //given
        val viewModel = multiViewModel

        //when
        viewModel.setMembers("성춘향")

        //then
        assertAll({
            val actual = viewModel.members1Player.getOrAwaitValue()
            assertThat(actual, isit("멀티 게임에서는 2명 이상의 player가 필요합니다."))
        }, {
            val actual = viewModel.members.getOrAwaitValue()
            assertThat(actual, isit(arrayOf("성춘향")))
        }, {
            val actual = viewModel.memberInput.getOrAwaitValue()
            assertThat(actual, isit(Unit))
        })
    }
}

