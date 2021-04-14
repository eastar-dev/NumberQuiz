package dev.eastar.numberquiz.main

import android.log.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.domain.TryNumberUseCase
import dev.eastar.enty.GameResult
import javax.inject.Inject

//app 에서는 domainModel 이 아닌 domainEntity 만 사용할 수 있도록 설계할 수 있을거 같아요
//이렇게 하면 presenter 여기서는 app 인데. domain 에 있는 모델만 쓰고 data 에 있는 모델을 사용하지 않는 구조로 만들수 있는데
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

