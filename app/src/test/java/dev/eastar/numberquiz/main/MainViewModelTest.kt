package dev.eastar.numberquiz.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.eastar.numberquiz.main.MainViewModel
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun exit() {
        val mainViewModel = MainViewModel()
        mainViewModel.exit()

        val observer = Observer<Unit> {}
        try {
            val value = mainViewModel.exit.value
            MatcherAssert.assertThat(value, `is`(Unit))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            mainViewModel.exit.removeObserver(observer)
        }
    }

    @Test
    fun startSinglePlay() {
        //val mainViewModel = MainViewModel()
        //mainViewModel.startSinglePlay()
        //
        //val observer = Observer<Class<out Fragment>> {}
        //try {
        //    val value = mainViewModel.moveFragment.value
        //    //MatcherAssert.assertThat(value, `is`(SingleFr::class))
        //    Assert.assertEquals(value, SingleFr::class)
        //
        //} finally {
        //    // Whatever happens, don't forget to remove the observer!
        //    mainViewModel.moveFragment.removeObserver(observer)
        //}
    }

    @Test
    fun startMultiPlay() {
        //val mainViewModel = MainViewModel()
        //mainViewModel.startMultiPlay()
        //
        //val observer = Observer<Class<out Fragment>> {}
        //try {
        //    val value = mainViewModel.moveFragment.value
        //    MatcherAssert.assertThat(value, `is`(MultiFr::class))
        //
        //} finally {
        //    // Whatever happens, don't forget to remove the observer!
        //    mainViewModel.moveFragment.removeObserver(observer)
        //}
    }
}