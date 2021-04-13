package dev.eastar.numberquiz.main

//import org.hamcrest.Matchers
//import org.hamcrest.Matchers.`is`

import android.log.Log
import android.util.getOrAwaitValue
import android.util.mock
import android.util.whenever
import androidx.lifecycle.Observer
import android.util.InstantExecutorExtension
import dev.eastar.numberquiz.data.repo.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
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
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private val gameRepository: GameRepository by lazy { mock() }

    @BeforeEach
    fun init() {
        Log.outputSystem()

        Dispatchers.setMain(testDispatcher)

        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        assertThat(gameRepository.generateRandomNumber(), CoreMatchers.`is`(5))
//        https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
//        verify(gameRepository, times(1)) //error X
        Mockito.verify(gameRepository, Mockito.times(1)).generateRandomNumber()
    }
    
    @AfterEach
    fun exit() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun tryNumber() {
        //given
        val viewModel = MultiViewModel(gameRepository)
        //when
        val observer = Observer<dev.eastar.domain.GameResult> {}
        viewModel.gameResult.observeForever(observer)
        viewModel.tryingNumber.value = "1"
        viewModel.tryNumber()

        try {
            //then
            val gameResult = dev.eastar.domain.GameResult.low
            val actual = viewModel.gameResult.value
            val actual2 = viewModel.gameEnd.value
            assertThat(actual, CoreMatchers.`is`(gameResult))
            assertThat(actual2, CoreMatchers.`is`(nullValue()))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameResult.removeObserver(observer)
        }
    }

    @ParameterizedTest
    @CsvSource(value = ["1,0", "3,0", "5,1", "7,2", "9,2"])
    fun tryNumber(number: String, result: Int) {
        //given
        val viewModel = MultiViewModel(gameRepository)
        //when
        val observer = Observer<dev.eastar.domain.GameResult> {}
        viewModel.gameResult.observeForever(observer)
        viewModel.tryingNumber.value = number
        viewModel.setMembers("성춘향,변사또")
        viewModel.tryNumber()

        try {
            //then
            val gameResult = dev.eastar.domain.GameResult.values()[result]
            val actual = viewModel.gameResult.value
            assertThat(actual, CoreMatchers.`is`(gameResult))

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
        val viewModel = MultiViewModel(gameRepository)
        //when
        val actual = viewModel.signumTest(number)
        //then
        assertThat(actual, CoreMatchers.`is`(result))

    }

    @Test
    fun tryNumber_correct() {
        //given
        val viewModel = MultiViewModel(gameRepository)
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
            assertThat(actual, CoreMatchers.`is`("축하합니다.\n승자는 성춘향 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameEnd.removeObserver(observer)
        }
    }

    @Test
    fun tryNumber_correct2() {
        //given
        val viewModel = MultiViewModel(gameRepository)
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
        val viewModel = MultiViewModel(gameRepository)

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
        val viewModel = MultiViewModel(gameRepository)
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
        val viewModel = MultiViewModel(gameRepository)
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
        val viewModel = MultiViewModel(gameRepository)

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

    @Test
    @DisplayName("Multi에서 입력받은유저가1명이면 2명이상필요하다요청한다")
    fun setMembers_1player_case2() = testDispatcher.runBlockingTest {
        //given
        val viewModel = MultiViewModel(gameRepository)

        //when
        viewModel.setMembers("성춘향")

        //then
        assertAll({
            val actual = viewModel.members1Player.getOrAwaitValue()
            assertThat(actual, `is`("멀티 게임에서는 2명 이상의 player가 필요합니다."))
        }, {
            val actual = viewModel.members.getOrAwaitValue()
            assertThat(actual, CoreMatchers.`is`(arrayOf("성춘향")))
        }, {
            val actual = viewModel.memberInput.getOrAwaitValue()
            assertThat(actual, CoreMatchers.`is`(Unit))
        })
    }

}

