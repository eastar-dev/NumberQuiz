package dev.eastar.numberquiz.main

import android.log.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.domain.TryNumberUseCase
import dev.eastar.enty.GameResult
import javax.inject.Inject

@HiltViewModel
class MultiViewModel @Inject constructor(private var tryNumberUseCase: TryNumberUseCase) : ViewModel() {

    val gameResult = MutableLiveData<GameResult>()
    val gameEnd = MutableLiveData<String>()
    val tryingNumber = MutableLiveData<String>()

    val members = MutableLiveData<Array<String>>()
    val memberInput: LiveData<Unit> = Transformations.switchMap(members) {
        if (it.size < 2)
            MutableLiveData(Unit)
        else
            null
    }
    val members1Player: LiveData<String> = Transformations.switchMap(members) {
        if (it.size == 1)
            MutableLiveData("멀티 게임에서는 2명 이상의 player가 필요합니다.")
        else
            null
    }


    init {
        setMembers("")
    }

    fun tryNumber() {
        val tryingNumber = tryingNumber.runCatching {
            value?.toInt()
        }.getOrNull()
        tryingNumber ?: return

        val result = tryNumberUseCase.tryNumber(tryingNumber)
        gameResult.value = result
        if (result == GameResult.correct)
            gameEnd.value = "축하합니다.\n승자는 ${tryNumberUseCase.winner} 입니다."
        Log.w(gameResult.value)
    }

    fun setMembers(membersText: String) {
        val playerArray = membersText.split(",").filter { it.isNotBlank() }.toTypedArray()
        //case1
        members.value = playerArray
//        //case2
//        members.postValue(playerArray)
//        //case3
//        Executors.newSingleThreadExecutor().execute {
//            members.postValue(playerArray)
//        }
//        //case4
//        viewModelScope.launch {
//            members.postValue(playerArray)
//        }
        tryNumberUseCase.player = playerArray
    }
}

