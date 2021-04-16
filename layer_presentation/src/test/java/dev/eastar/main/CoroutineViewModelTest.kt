package dev.eastar.main

import android.log.Log
import android.util.InstantExecutorExtension
import android.util.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class CoroutineViewModelTest {
    companion object {
        @JvmField
        @RegisterExtension
        val coroutineExtension = MainCoroutineExtension()
    }

    @BeforeEach
    fun setUp() {
        Log.e("start - setUp")
    }

    @AfterEach
    fun tearDown() {
        Log.e("start - tearDown")
    }

    @Test
    fun setMembersAsync() = coroutineExtension.runBlockingTest {
        Log.e("\tstart - setMembersAsyncTest")
        //given
        val viewModel = CoroutineViewModel()
        viewModel.members.observeForever {}
        //when
        Log.e("\tstart - viewModel.setMembersAsync(\"성춘향\")")
        viewModel.setMembersAsync("성춘향")
        Log.e("\tend - viewModel.setMembersAsync(\"성춘향\")")
        //Log.e("\tnothing - advanceUntilIdle")
        Log.e("\tstart - advanceUntilIdle")
        coroutineExtension.advanceUntilIdle()
        Log.e("\tend - advanceUntilIdle")

        //then
        val actual = viewModel.members.value
        assertThat(actual, CoreMatchers.`is`(arrayOf("성춘향")))
        Log.e("\tend - setMembersAsyncTest")
    }

    @Test
    fun setMembersAsyncFail() = coroutineExtension.runBlockingTest {
        Log.e("\tstart - setMembersAsyncTest")
        //given
        val viewModel = CoroutineViewModel()
        viewModel.members.observeForever {}
        //when
        Log.e("\tstart - viewModel.setMembersAsync(\"성춘향\")")
        viewModel.setMembersAsync("성춘향")
        Log.e("\tend - viewModel.setMembersAsync(\"성춘향\")")
        Log.e("\tnothing - advanceUntilIdle")

        //then
        val actual = viewModel.members.value
        assertThat(actual, CoreMatchers.not(arrayOf("성춘향")))
        Log.e("\tend - setMembersAsyncTest")
    }
}

