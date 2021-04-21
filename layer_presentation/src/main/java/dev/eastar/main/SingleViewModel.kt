package dev.eastar.main

import android.log.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.usecase.GameSingleRoundUseCase
import dev.eastar.entity.RoundResultEntity
import javax.inject.Inject

//app 에서는 domainModel 이 아닌 domainEntity 만 사용할 수 있도록 설계할 수 있을거 같아요
//이렇게 하면 presenter 여기서는 app 인데. domain 에 있는 모델만 쓰고 data 에 있는 모델을 사용하지 않는 구조로 만들수 있는데
@HiltViewModel
class SingleViewModel @Inject constructor(private var gameSingleRoundUseCase: GameSingleRoundUseCase) : ViewModel() {
    val TryResultEntity = MutableLiveData<RoundResultEntity>()
    val gameEnd = MutableLiveData<String>()
    val tryingNumber = MutableLiveData<String>()

    fun tryNumber() {
        val tryingNumber = tryingNumber.runCatching {
            value?.toInt()
        }.getOrNull()
        tryingNumber ?: return

        val case = gameSingleRoundUseCase
        val entity = case.tryNumber(tryingNumber)

        TryResultEntity.value = entity.tryResult
        if (entity.isEndGame)
            gameEnd.value = "축하합니다. 총시도 횟수는 ${entity.tryCount}번 입니다."
        Log.w(TryResultEntity.value)
    }
}

