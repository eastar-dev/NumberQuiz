package dev.eastar.main

import android.log.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.entity.TryResultEntity
import dev.eastar.usecase.TryNumberUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

fun ViewModel.getViewModelScope(coroutineScope: CoroutineScope?) = coroutineScope ?: this.viewModelScope

@HiltViewModel
class MultiViewModel @Inject constructor(
    private var tryNumberUseCase: TryNumberUseCase,
    private val coroutineScopeProvider: CoroutineScope? = null,
) : ViewModel() {
    private val viewModelScope = getViewModelScope(coroutineScopeProvider)

    val TryResultEntity = MutableLiveData<TryResultEntity>()
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

        TryResultEntity.value = entity.tryResult
        if (entity.isEndGame)
            gameEnd.value = "축하합니다.\n승자는 ${entity.winner} 입니다."
        Log.w(TryResultEntity.value)
    }

    fun setMembers(membersText: String) {
        val playerArray = membersText.split(",").filter { it.isNotBlank() }.toTypedArray()
        members.value = playerArray
        tryNumberUseCase.setPlayers(playerArray)
    }

    fun setMembersAsync(membersText: String) = viewModelScope.launch {
        Log.w("start - setMembersAsync")
        delay(1)
        Log.w("delay - setMembersAsync")
        val playerArray = membersText.split(",").filter { it.isNotBlank() }.toTypedArray()
        Log.w()
        members.value = playerArray
        Log.w()
        tryNumberUseCase.setPlayers(playerArray)
        Log.w("end - setMembersAsync")
    }
}

