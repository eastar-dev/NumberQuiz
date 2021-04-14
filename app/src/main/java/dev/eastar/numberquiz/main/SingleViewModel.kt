package dev.eastar.numberquiz.main

import android.log.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.domain.TryNumberUseCase
import dev.eastar.enty.GameResult
import javax.inject.Inject

@HiltViewModel
class SingleViewModel @Inject constructor(private var tryNumberUseCase: TryNumberUseCase) : ViewModel() {
    val gameResult = MutableLiveData<GameResult>()
    val gameEnd = MutableLiveData<String>()
    val tryingNumber = MutableLiveData<String>()

    fun tryNumber() {
        val tryingNumber = tryingNumber.runCatching {
            value?.toInt()
        }.getOrNull()
        tryingNumber ?: return

        val gd = tryNumberUseCase
        val lowHigh = gd.tryNumber(tryingNumber)

        gameResult.value = lowHigh
        if (lowHigh == GameResult.correct)
            gameEnd.value = "축하합니다. 총시도 횟수는 ${gd.tryCount}번 입니다."
        Log.w(gameResult.value)
    }
}

