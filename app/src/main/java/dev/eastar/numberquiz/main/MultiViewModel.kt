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
class MultiViewModel @Inject constructor(gameRepository: GameRepository) : ViewModel() {
    private var tryCount: Int = 0
    private val number = gameRepository.generateRandomNumber()

    init {
        Log.e("generateRandomNumber", number)
    }

    val gameResult = MutableLiveData<GameResult>()
    val gameEnd = MutableLiveData<String>()
    val tryingNumber = MutableLiveData<String>()

    fun tryNumber() {
        Log.e(tryingNumber.value, number)
        val tryingNumber = tryingNumber.runCatching {
            value?.toInt()
        }.getOrNull()
        tryingNumber ?: return

        val result = signumTest(tryingNumber)
        val lowHigh = GameResult.values()[result + 1]
        gameResult.value = lowHigh
        tryCount++
        if(lowHigh == GameResult.correct)
            gameEnd.value = "축하합니다. 총시도 횟수는 ${tryCount}번 입니다."
        Log.w(gameResult.value)
    }

    @VisibleForTesting
    fun signumTest(number: Int): Int {
        return Integer.signum(number - this.number)
    }
}

