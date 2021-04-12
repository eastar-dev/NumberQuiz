package dev.eastar.numberquiz.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import android.util.InstantExecutorExtension
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest {

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
        val mainViewModel = MainViewModel()
        mainViewModel.startSinglePlay()

        val observer = Observer<Class<out Fragment>> {}
        try {
            val value = mainViewModel.moveFragment.value
//            MatcherAssert.assertThat("way not ? ",value, `is`(SingleFr::class) )
            Assert.assertEquals(value, SingleFr::class.java)

        } finally {
            // Whatever happens, don't forget to remove the observer!
            mainViewModel.moveFragment.removeObserver(observer)
        }
    }

    @Test
    fun startMultiPlay() {
        val mainViewModel = MainViewModel()
        mainViewModel.startMultiPlay()

        val observer = Observer<Class<out Fragment>> {}
        try {
            val value = mainViewModel.moveFragment.value
            Assert.assertEquals(value, MultiFr::class.java)
        } finally {
            // Whatever happens, don't forget to remove the observer!
            mainViewModel.moveFragment.removeObserver(observer)
        }
    }
}