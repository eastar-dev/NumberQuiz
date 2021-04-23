package dev.eastar.main

import junit.util.InstantExecutorExtension
import junit.util.mock
import junit.util.whenever
import androidx.lifecycle.Observer
import dev.eastar.entity.RoundResult
import dev.eastar.repository.GameRepository
import dev.eastar.usecase.GameRoundUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
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
    private lateinit var gameRoundUseCase: GameRoundUseCase

    @BeforeEach
    fun setUp() {
        val gameRepository: GameRepository by lazy { mock() }
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        assertThat(gameRepository.generateRandomNumber(), CoreMatchers.`is`(5))
        //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
        Mockito.verify(gameRepository, Mockito.times(1)).generateRandomNumber()

        gameRoundUseCase = GameRoundUseCase(gameRepository)
    }

    @Test
    fun tryNumber() {
        //given
        val viewModel = MultiViewModel(gameRoundUseCase)
        //when
        val observer = Observer<RoundResult> {}
        viewModel.tryResultEntity.observeForever(observer)
        viewModel.tryingNumber.value = "1"
        viewModel.tryNumber()

        try {
            //then
            val TryResultEntity = RoundResult.LOW
            val actual = viewModel.tryResultEntity.value
            val actual2 = viewModel.gameEnd.value
            assertThat(actual, CoreMatchers.`is`(TryResultEntity))
            assertThat(actual2, CoreMatchers.`is`(nullValue()))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.tryResultEntity.removeObserver(observer)
        }
    }

    @ParameterizedTest
    @CsvSource(value = ["1,0", "3,0", "5,1", "7,2", "9,2"])
    fun tryNumber(number: String, result: Int) {
        //given
        val viewModel = MultiViewModel(gameRoundUseCase)
        //when
        val observer = Observer<RoundResult> {}
        viewModel.tryResultEntity.observeForever(observer)
        viewModel.tryingNumber.value = number
        viewModel.setMembers("성춘향,변사또")
        viewModel.tryNumber()

        try {
            //then
            val TryResultEntity = RoundResult.values()[result]
            val actual = viewModel.tryResultEntity.value
            assertThat(actual, CoreMatchers.`is`(TryResultEntity))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.tryResultEntity.removeObserver(observer)
        }
    }

    //    @ValueSource(ints = [1,3,5,7,9])
//    @ParameterizedTest
//    @CsvSource(value = ["1,-1", "3,-1", "5,0", "7,+1", "9,+1"])
//    fun signmunTest(number: Int, result: Int) {
//        //given
//        val viewModel = MultiViewModel(tryNumberUseCase)
//        //when
//        val actual = viewModel.signumTest(number)
//        //then
//        assertThat(actual, CoreMatchers.`is`(result))
//
//    }

    @Test
    fun tryNumber_correct() {
        //given
        val viewModel = MultiViewModel(gameRoundUseCase)
        //when
        val observer = Observer<String> {}
        viewModel.gameEnd.observeForever(observer)
        viewModel.setMembers("성춘향,변사또")

        arrayOf("5").forEach {
            viewModel.tryingNumber.value = it
            viewModel.tryNumber()
        }

        try {
            //then
            val actual = viewModel.gameEnd.value
            assertThat(actual, CoreMatchers.`is`("축하합니다.\n승자는 성춘향 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameEnd.removeObserver(observer)
        }
    }

    @Test
    fun tryNumber_correct2() {
        //given
        val viewModel = MultiViewModel(gameRoundUseCase)
        //when
        val observer = Observer<String> {}
        viewModel.gameEnd.observeForever(observer)
        viewModel.setMembers("성춘향,변사또")

        arrayOf("1", "5").forEach {
            viewModel.tryingNumber.value = it
            viewModel.tryNumber()
        }
        try {
            //then
            val actual = viewModel.gameEnd.value
            assertThat(actual, CoreMatchers.`is`("축하합니다.\n승자는 변사또 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameEnd.removeObserver(observer)
        }
    }

    @ExperimentalCoroutinesApi
    @ParameterizedTest
    @EmptySource //"" test를 자동으로 해준다.
    @ValueSource(strings = arrayOf(" ", ",", "  , , , ,", "\t   , , , ,"))
    @DisplayName("""Multi에서 입력받은유저가""면 요청한다""")
    fun setMembers_empty(input: String) {
        //given
        val viewModel = MultiViewModel(gameRoundUseCase)

        //when
        viewModel.setMembers(input)
        viewModel.memberInput.observeForever {}
        viewModel.members.observeForever {}
//            delay(2000)
        //then
        assertAll({
            val actual = viewModel.memberInput.value
            assertThat(actual, CoreMatchers.`is`(Unit))
        }, {
            val actual = viewModel.members.value
            assertThat(actual, CoreMatchers.`is`(emptyArray()))
        })

    }

    @Test
    @DisplayName("Multi에서 시작시emptyplayer 요청")
    fun setMembers_empty() {
        //given
        val viewModel = MultiViewModel(gameRoundUseCase)
        //when
        val observer = Observer<Unit> { actual ->
            Assertions.assertEquals(Unit, actual)
        }
        viewModel.memberInput.observeForever(observer)

        //then
        val actual = viewModel.memberInput.value
        Assertions.assertEquals(Unit, actual)
        viewModel.memberInput.removeObserver(observer)
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가>2면 입력요청안한다")
    fun setMembers_over2_member() {
        //given
        val viewModel = MultiViewModel(gameRoundUseCase)
        //when
        viewModel.setMembers("성춘향,변사또")
        val observer = Observer<Unit> { Assertions.fail() }
        viewModel.memberInput.observeForever(observer)

        //then
        val actual = viewModel.memberInput.value
        Assertions.assertNull(actual)
        viewModel.memberInput.removeObserver(observer)
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가1명이면 2명이상필요하다요청한다")
    fun setMembers_1player() {
        //given
        val viewModel = MultiViewModel(gameRoundUseCase)

        //when
        viewModel.setMembers("성춘향")
        viewModel.members1Player.observeForever {}
        viewModel.memberInput.observeForever {}
        viewModel.members.observeForever {}

        //then
        assertAll({
            val actual = viewModel.members1Player.value
            assertThat(actual, `is`("멀티 게임에서는 2명 이상의 player가 필요합니다."))
        }, {
            val actual = viewModel.members.value
            assertThat(actual, CoreMatchers.`is`(arrayOf("성춘향")))
        }, {
            val actual = viewModel.memberInput.value
            assertThat(actual, CoreMatchers.`is`(Unit))
        })
    }
}

