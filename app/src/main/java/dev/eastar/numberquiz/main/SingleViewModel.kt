package dev.eastar.numberquiz.main

import android.log.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.numberquiz.data.GameResult
import dev.eastar.numberquiz.data.repo.GameRepository
import javax.inject.Inject

@HiltViewModel
class SingleViewModel @Inject constructor(private val gameRepository: GameRepository) :
    ViewModel() {
    val number = gameRepository.generateRandomNumber()

    init {
        Log.e("generateRandomNumber", number)

    }

    val gameResult = MutableLiveData<GameResult>()
    val tryingNumber = MutableLiveData<String>()

    fun tryNumber() {
        val tryingNumber = tryingNumber.runCatching {
            value?.toInt()
        }.getOrNull()
        tryingNumber ?: return

        val result = signumTest(tryingNumber)
        gameResult.value = GameResult.values()[result + 1]
    }

    @VisibleForTesting
    fun signumTest(number: Int): Int {
        return Integer.signum(number - this.number)
    }
}

