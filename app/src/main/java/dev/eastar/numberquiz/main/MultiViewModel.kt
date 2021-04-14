package dev.eastar.numberquiz.main

import android.log.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.domain.TryNumberUseCase
import dev.eastar.enty.GameResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiViewModel @Inject constructor(private var tryNumberUseCase: TryNumberUseCase) :
    ViewModel() {

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
        val case = tryNumberUseCase
        val lowHigh = case.tryNumber(tryingNumber)
        gameResult.value = lowHigh
        if (lowHigh == GameResult.correct)
            gameEnd.value = "축하합니다.\n승자는 ${case.winner} 입니다."
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
        //case4
//        viewModelScope.launch {
//            delay(100)
//            members.postValue(playerArray)
//        }
        tryNumberUseCase.player = playerArray
    }

    suspend fun setMembers2(membersText: String) {
        Log.e()
        viewModelScope.launch(Dispatchers.Main) {
        Log.e()
            delay(100)
        Log.e()
            val playerArray = membersText.split(",").filter { it.isNotBlank() }.toTypedArray()
        Log.e()
            delay(100)
        Log.e(playerArray)
            members.postValue(playerArray)
        Log.e()
            tryNumberUseCase.player = playerArray
        Log.e()
        }
        Log.e()
    }
}

