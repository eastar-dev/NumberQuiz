package dev.eastar.numberquiz.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.eastar.numberquiz.data.GameResult
import dev.eastar.numberquiz.data.repo.GameRepository

class SingleViewModel constructor(private val gameRepository: GameRepository) : ViewModel() {
    val number = gameRepository.generateRandomNumber()

    val gameResult = MutableLiveData<GameResult>()

    fun tryNumber(number: Int) {
        val result = signumTest(number)
        gameResult.value = GameResult.values()[result + 1]
    }

    @VisibleForTesting
    fun signumTest(number: Int): Int {
        return Integer.signum(number - this.number)
    }
}

