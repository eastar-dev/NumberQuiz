package dev.eastar.numberquiz.main

import android.log.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.domain.GameDomain
import dev.eastar.enty.GameResult
import dev.eastar.numberquiz.data.repo.GameRepository
import javax.inject.Inject

@HiltViewModel
class SingleViewModel @Inject constructor(gameRepository: GameRepository) : ViewModel() {
//    private var tryCount: Int = 0
    private val number = gameRepository.generateRandomNumber()
    private val gameDomain = GameDomain(number)

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

        val gd = gameDomain
        val lowHigh = gd.tryNumber(tryingNumber)

        gameResult.value = lowHigh
        if (lowHigh == GameResult.correct)
            gameEnd.value = "축하합니다. 총시도 횟수는 ${gd.tryCount}번 입니다."
        Log.w(gameResult.value)
    }

    @VisibleForTesting
    fun signumTest(number: Int): Int {
        return Integer.signum(number - this.number)
    }
}

