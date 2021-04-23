package viewmodeldemo

import android.log.Log
import junit.util.InstantExecutorExtension
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*


@ExtendWith(InstantExecutorExtension::class)
class ExecutorsViewModelTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
        Log.e("end - tearDown")
    }

    @Test
    @DisplayName("Multi에서 입력받은유저가1명이면 2명이상필요하다요청한다")
    fun setMembersAsyncAwait()  {
        //given
        val viewModel = ExecutorsViewModel()

        //when
        Log.e("\tgetOrAwaitValue")
        viewModel.testSetMembersExecutorsRunner("성춘향")
        viewModel.members.observeForever{}

        //then
        Log.e("\tgetOrAwaitValue")
        val actual = viewModel.members.value
        Log.e("\tgetOrAwaitValue", Arrays.toString(actual))
        assertThat(actual, CoreMatchers.`is`(arrayOf("성춘향")))
    }

}

