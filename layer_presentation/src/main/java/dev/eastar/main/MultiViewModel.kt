package dev.eastar.main

import android.log.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.entity.TryResultEntity
import dev.eastar.usecase.TryNumberUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

fun ViewModel.getViewModelScope(coroutineScope: CoroutineScope?) = coroutineScope ?: this.viewModelScope

@HiltViewModel
class MultiViewModel @Inject constructor(private var tryNumberUseCase: TryNumberUseCase) : ViewModel() {

    val tryResultEntity = MutableLiveData<TryResultEntity>()
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
        val entity = case.tryNumber(tryingNumber)

        tryResultEntity.value = entity.tryResult
        if (entity.isEndGame)
            gameEnd.value = "축하합니다.\n승자는 ${entity.winner} 입니다."
        Log.w(tryResultEntity.value)
    }

    fun setMembers(membersText: String) {
        val playerArray = membersText.split(",").filter { it.isNotBlank() }.toTypedArray()
        members.value = playerArray
        tryNumberUseCase.setPlayers(playerArray)
    }

    fun setMembersAsync(membersText: String) = viewModelScope.launch {
        Log.w("\t\tstart - setMembersAsync")
        Log.w("\t\tdelay start - setMembersAsync delay(1)")
        delay(1000)
        Log.w("\t\tdelay end - setMembersAsync delay(1)")
        val playerArray = membersText.split(",").filter { it.isNotBlank() }.toTypedArray()
        members.value = playerArray
        Log.w("\t\tend - setMembersAsync")
        tryNumberUseCase.setPlayers(playerArray)
    }

    fun setMembersExecutors(membersText: String) = Executors.newSingleThreadExecutor().submit(setMembersExecutorsRunner(membersText))

    @VisibleForTesting
    fun testSetMembersExecutorsRunner(membersText: String) = setMembersExecutorsRunner(membersText).run()

    private fun setMembersExecutorsRunner(membersText: String) = Runnable {
        Log.w("\t\tstart - setMembersAsync")
        Thread.sleep(1_000)
        Log.w("\t\tdelay - setMembersAsync")
        val playerArray = membersText.split(",").filter { it.isNotBlank() }.toTypedArray()
        Log.w("\t\tset membersText")
        members.value = playerArray
        Log.w("\t\tset membersText")
        tryNumberUseCase.setPlayers(playerArray)
    }


}
