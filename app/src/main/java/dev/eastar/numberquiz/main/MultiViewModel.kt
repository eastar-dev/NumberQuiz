package dev.eastar.numberquiz.main

import android.log.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.numberquiz.data.GameResult
import dev.eastar.numberquiz.data.repo.GameRepository
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class MultiViewModel @Inject constructor(gameRepository: GameRepository) : ViewModel() {
    private var tryCount: Int = 0
    private val number = gameRepository.generateRandomNumber()


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
        Log.e("generateRandomNumber", number)
        setMembers("")
    }

    fun tryNumber() {
        Log.e(tryingNumber.value, number)
        val tryingNumber = tryingNumber.runCatching {
            value?.toInt()
        }.getOrNull()
        tryingNumber ?: return

        val result = signumTest(tryingNumber)
        val lowHigh = GameResult.values()[result + 1]
        gameResult.value = lowHigh
        if (lowHigh == GameResult.correct) {
            members.value?.getOrNull(0) ?: return
            val members = members.value!!
            val winner = members[tryCount % members.size]
            gameEnd.value = "축하합니다.\n승자는 $winner 입니다."
        }
        Log.w(gameResult.value)
        tryCount++
    }

    @VisibleForTesting
    fun signumTest(number: Int): Int {
        return Integer.signum(number - this.number)
    }

    fun setMembers(membersText: String) {
        //case1
        members.value = membersText.split(",").filter { it.isNotBlank() }.toTypedArray()
        //case2
//        members.postValue(membersText.split(",").filter { it.isNotBlank() }.toTypedArray())
        //case3
//        Executors.newSingleThreadExecutor().execute {
//            members.postValue(membersText.split(",").filter { it.isNotBlank() }.toTypedArray())
//        }
        //case4
//        viewModelScope.launch {
//            members.postValue(membersText.split(",").filter { it.isNotBlank() }.toTypedArray())
//        }
    }
}

