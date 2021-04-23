package dev.eastar.main

import android.log.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eastar.entity.RoundResult
import dev.eastar.usecase.MultiGameRoundUseCase
import dev.eastar.usecase.MultiGameStartUseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

fun ViewModel.getViewModelScope(coroutineScope: CoroutineScope?) = coroutineScope ?: this.viewModelScope

@HiltViewModel
class MultiViewModel @Inject constructor(
    private var multiGameStartUseCase: MultiGameStartUseCase,
    private var multiGameRoundUseCase: MultiGameRoundUseCase
) : ViewModel() {

    val roundResult = MutableLiveData<RoundResult>()
    val gameEnd = MutableLiveData<String>()
    val guess = MutableLiveData<String>()

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
        val guess = guess.runCatching {
            value?.toInt()
        }.getOrNull()
        guess ?: return

        val entity = multiGameRoundUseCase(guess)

        roundResult.value = entity.roundResult
        if (entity.isEndGame)
            gameEnd.value = "축하합니다.\n승자는 ${entity.winner} 입니다."
    }

    fun setMembers(membersText: String) {
        val players = membersText.split(",").filter { it.isNotBlank() }.toTypedArray()
        members.value = players
        if (players.size >= 2)
            multiGameStartUseCase(players)
    }
}
