package dev.eastar.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val exit = MutableLiveData<Unit>()
    val moveMultiGame = MutableLiveData<Unit>()
    val moveSingleGame = MutableLiveData<Unit>()

    fun exit() {
        exit.value = Unit
    }

    fun startSinglePlay() {
        moveSingleGame.value = Unit
    }

    fun startMultiPlay() {
        moveMultiGame.value = Unit
    }
}