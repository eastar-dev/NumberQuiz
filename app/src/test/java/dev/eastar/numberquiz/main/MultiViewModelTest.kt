package dev.eastar.numberquiz.main

import android.log.Log
import androidx.lifecycle.Observer
import dev.eastar.numberquiz.InstantExecutorExtension
import dev.eastar.numberquiz.data.GameResult
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantExecutorExtension::class)
class MultiViewModelTest {
    @BeforeEach
    fun init() {
        Log.outputSystem()
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
            assertThat(actual, CoreMatchers.`is`(gameResult))
            assertThat(actual2, CoreMatchers.`is`(Matchers.nullValue()))

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
        viewModel.members.value = arrayOf("성춘향,변사또")
        viewModel.tryNumber()

        try {
            //then
            val gameResult = GameResult.values()[result]
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
        val viewModel = MultiViewModel(GameRepositoryFack())
        //when
        val actual = viewModel.signumTest(number)
        //then
        assertThat(actual, CoreMatchers.`is`(result))

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
            assertThat(actual, CoreMatchers.`is`("축하합니다.\n승자는 변사또 입니다."))

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
            assertThat(actual, CoreMatchers.`is`("축하합니다.\n승자는 성춘향 입니다."))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.gameEnd.removeObserver(observer)
        }
    }

    @Test
    @DisplayName("""Multi에서 입력받은유저가""면 요청한다""")
    fun setMembers_empty() {
        //given
        val viewModel = MultiViewModel(GameRepositoryFack())

        //when
        val observer = Observer<Unit> { Log.e("Observer") }
        viewModel.setMembers("")
        viewModel.membersEmpty.observeForever(observer)

        //then
        assertAll({
            val actual = viewModel.membersEmpty.value
            assertThat(actual, CoreMatchers.`is`(Unit))
        }, {
            val actual = viewModel.members.value
            assertThat(actual, CoreMatchers.`is`(emptyArray()))
        })


        viewModel.membersEmpty.removeObserver(observer)
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가있으면 입력요청안한다")
    fun setMembers_not_empty() {
        //given
        val viewModel = MultiViewModel(GameRepositoryFack())
        //when
        val observer = Observer<Unit> {
            Assertions.fail()
        }
        viewModel.membersEmpty.observeForever(observer)
        viewModel.setMembers("성춘")


        //then
        val actual = viewModel.membersEmpty.value
        Assertions.assertNull(actual)
        viewModel.membersEmpty.removeObserver(observer)
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가1명이면 2명이상필요하다요청한다")
    fun setMembers_1player() {
        //given
        val viewModel = MultiViewModel(GameRepositoryFack())

        //when
        val observer = Observer<String> {}
        viewModel.members1Player.observeForever(observer)
        viewModel.setMembers("성춘향")
        //then
        val actual = viewModel.members1Player.value
        assertThat(actual, `is`("멀티 게임에서는 2명 이상의 player가 필요합니다."))
        viewModel.members1Player.removeObserver(observer)
    }
}

