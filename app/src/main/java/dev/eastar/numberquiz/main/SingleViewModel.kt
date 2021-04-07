package dev.eastar.numberquiz.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.eastar.numberquiz.data.GameResult
import dev.eastar.numberquiz.data.repo.GameRepository

class SingleViewModel constructor(private val gameRepository: GameRepository) : ViewModel() {
    val number = gameRepository.generateRandomNumber()

    val gameResult = MutableLiveData<GameResult>()

    fun tryNumber(number: Int) {
        gameResult.value = GameResult.high
    }
}

