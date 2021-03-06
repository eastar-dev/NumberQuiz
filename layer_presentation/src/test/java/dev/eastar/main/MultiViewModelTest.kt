package dev.eastar.main

import android.util.InstantExecutorExtension
import android.util.getOrAwaitValue
import android.util.mock
import android.util.whenever
import androidx.lifecycle.Observer
import dev.eastar.entity.TryResultEntity
import dev.eastar.repository.GameRepository
import dev.eastar.usecase.TryNumberUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
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

    private lateinit var tryNumberUseCase: TryNumberUseCase

    @BeforeEach
    fun setUp() {
        val gameRepository: GameRepository by lazy { mock() }
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        assertThat(gameRepository.generateRandomNumber(), CoreMatchers.`is`(5))
        //https://velog.io/@dnjscksdn98/JUnit-Mockito-Verify-Method-Calls
        Mockito.verify(gameRepository, Mockito.times(1)).generateRandomNumber()

        tryNumberUseCase = TryNumberUseCase(gameRepository)
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
        val viewModel = MultiViewModel(tryNumberUseCase)
        //when
        val observer = Observer<TryResultEntity> {}
        viewModel.TryResultEntity.observeForever(observer)
        viewModel.tryingNumber.value = "1"
        viewModel.tryNumber()

        try {
            //then
            val TryResultEntity = TryResultEntity.low
            val actual = viewModel.TryResultEntity.value
            val actual2 = viewModel.gameEnd.value
            assertThat(actual, CoreMatchers.`is`(TryResultEntity))
            assertThat(actual2, CoreMatchers.`is`(nullValue()))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.TryResultEntity.removeObserver(observer)
        }
    }

    @ParameterizedTest
    @CsvSource(value = ["1,0", "3,0", "5,1", "7,2", "9,2"])
    fun tryNumber(number: String, result: Int) {
        //given
        val viewModel = MultiViewModel(tryNumberUseCase)
        //when
        val observer = Observer<TryResultEntity> {}
        viewModel.TryResultEntity.observeForever(observer)
        viewModel.tryingNumber.value = number
        viewModel.setMembers("?????????,?????????")
        viewModel.tryNumber()

        try {
            //then
            val TryResultEntity = TryResultEntity.values()[result]
            val actual = viewModel.TryResultEntity.value
            assertThat(actual, CoreMatchers.`is`(TryResultEntity))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.TryResultEntity.removeObserver(observer)
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
        val viewModel = MultiViewModel(tryNumberUseCase)
        //when
        val observer = Observer<String> {}
        viewModel.gameEnd.observeForever(observer)
        viewModel.setMembers("?????????,?????????")

        arrayOf("5").forEach {
            viewModel.tryingNumber.value = it
            viewModel.tryNumber()
        }

        try {
            //then
            val actual = viewModel.gameEnd.value
            assertThat(actual, CoreMatchers.`is`("???????????????.\n????????? ????????? ?????????."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameEnd.removeObserver(observer)
        }
    }

    @Test
    fun tryNumber_correct2() {
        //given
        val viewModel = MultiViewModel(tryNumberUseCase)
        //when
        val observer = Observer<String> {}
        viewModel.gameEnd.observeForever(observer)
        viewModel.setMembers("?????????,?????????")

        arrayOf("1", "5").forEach {
            viewModel.tryingNumber.value = it
            viewModel.tryNumber()
        }
        try {
            //then
            val actual = viewModel.gameEnd.value
            assertThat(actual, CoreMatchers.`is`("???????????????.\n????????? ????????? ?????????."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameEnd.removeObserver(observer)
        }
    }

    @ExperimentalCoroutinesApi
    @ParameterizedTest
    @EmptySource //"" test??? ???????????? ?????????.
    @ValueSource(strings = arrayOf(" ", ",", "  , , , ,", "\t   , , , ,"))
    @DisplayName("""Multi?????? ?????????????????????""??? ????????????""")
    fun setMembers_empty(input: String) {
        //given
        val viewModel = MultiViewModel(tryNumberUseCase)

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
    @DisplayName("Multi?????? ?????????emptyplayer ??????")
    fun setMembers_empty() {
        //given
        val viewModel = MultiViewModel(tryNumberUseCase)
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
    @DisplayName("Multi?????? ?????????????????????>2??? ?????????????????????")
    fun setMembers_over2_member() {
        //given
        val viewModel = MultiViewModel(tryNumberUseCase)
        //when
        viewModel.setMembers("?????????,?????????")
        val observer = Observer<Unit> { Assertions.fail() }
        viewModel.memberInput.observeForever(observer)

        //then
        val actual = viewModel.memberInput.value
        Assertions.assertNull(actual)
        viewModel.memberInput.removeObserver(observer)
    }

    @Test
    @DisplayName("Multi?????? ?????????????????????1????????? 2?????????????????????????????????")
    fun setMembers_1player() {
        //given
        val viewModel = MultiViewModel(tryNumberUseCase)

        //when
        viewModel.setMembers("?????????")
        viewModel.members1Player.observeForever {}
        viewModel.memberInput.observeForever {}
        viewModel.members.observeForever {}

        //then
        assertAll({
            val actual = viewModel.members1Player.value
            assertThat(actual, `is`("?????? ??????????????? 2??? ????????? player??? ???????????????."))
        }, {
            val actual = viewModel.members.value
            assertThat(actual, CoreMatchers.`is`(arrayOf("?????????")))
        }, {
            val actual = viewModel.memberInput.value
            assertThat(actual, CoreMatchers.`is`(Unit))
        })
    }

    @Test
    @DisplayName("Multi?????? ?????????????????????1????????? 2?????????????????????????????????")
    fun setMembers_1player_case2() = testDispatcher.runBlockingTest {
        //given
        val viewModel = MultiViewModel(tryNumberUseCase)

        //when
        viewModel.setMembers("?????????")

        //then
        assertAll({
            val actual = viewModel.members1Player.getOrAwaitValue()
            assertThat(actual, `is`("?????? ??????????????? 2??? ????????? player??? ???????????????."))
        }, {
            val actual = viewModel.members.getOrAwaitValue()
            assertThat(actual, CoreMatchers.`is`(arrayOf("?????????")))
        }, {
            val actual = viewModel.memberInput.getOrAwaitValue()
            assertThat(actual, CoreMatchers.`is`(Unit))
        })
    }

}

