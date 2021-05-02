package dev.eastar.main

import android.log.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.entity.RoundResult
import dev.eastar.usecase.GameRoundUseCase
import dev.eastar.usecase.GameStartUseCase
import javax.inject.Inject

//app 에서는 domainModel 이 아닌 domainEntity 만 사용할 수 있도록 설계할 수 있을거 같아요
//이렇게 하면 presenter 여기서는 app 인데. domain 에 있는 모델만 쓰고 data 에 있는 모델을 사용하지 않는 구조로 만들수 있는데
@HiltViewModel
class SingleViewModel @Inject constructor(
    gameStartUseCase: GameStartUseCase,
    private var gameRoundUseCase: GameRoundUseCase
) : ViewModel() {
    val roundResult = MutableLiveData<RoundResult>()
    val endGameMsg = MutableLiveData<String>()
    val guess = MutableLiveData<String>()

    init {
        gameStartUseCase()
    }

    fun round() {
        val guess = guess.runCatching {
            value?.toInt()
        }.getOrNull()
        guess ?: return

        val entity = gameRoundUseCase(guess)

        roundResult.value = entity.roundResult
        if (entity.isEndGame)
            endGameMsg.value = "축하합니다. 총시도 횟수는 ${entity.roundCount}번 입니다."
        Log.w(roundResult.value)
    }
}

