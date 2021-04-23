package dev.eastar.main

import androidx.lifecycle.Observer
import junit.util.InstantExecutorExtension
import junit.util.isit
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest {

    @Test
    fun exit() {
        //given
        val mainViewModel = MainViewModel()

        //when
        mainViewModel.exit()
        val observer = Observer<Unit> {}

        try {
            //then
            val actual = mainViewModel.exit.value
            assertThat(actual, isit(Unit))

        } finally {
            mainViewModel.exit.removeObserver(observer)
        }
    }

    @Test
    fun startSinglePlay() {
        //given
        val mainViewModel = MainViewModel()

        //when
        mainViewModel.startSinglePlay()
        val observer = Observer<Unit> {}

        try {
            //then
            val actual = mainViewModel.moveSingleGame.value
            assertThat(actual, isit(Unit))
        } finally {
            mainViewModel.moveSingleGame.removeObserver(observer)
        }
    }

    @Test
    fun startMultiPlay() {
        //given
        val mainViewModel = MainViewModel()

        //when
        mainViewModel.startMultiPlay()
        val observer = Observer<Unit> {}

        try {
            //then
            val actual = mainViewModel.moveMultiGame.value
            assertThat(actual, isit(Unit))
        } finally {
            mainViewModel.moveMultiGame.removeObserver(observer)
        }
    }
}