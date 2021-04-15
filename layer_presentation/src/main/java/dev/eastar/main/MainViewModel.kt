package dev.eastar.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val exit = MutableLiveData<Unit>()
    val moveFragment = MutableLiveData<Class<out Fragment>>()

    fun exit() {
        exit.value = Unit
    }

    fun startSinglePlay() {
        moveFragment.value = SingleFr::class.java
    }

    fun startMultiPlay() {
        moveFragment.value = MultiFr::class.java
    }
}