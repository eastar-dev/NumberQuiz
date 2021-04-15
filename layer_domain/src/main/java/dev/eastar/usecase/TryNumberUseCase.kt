package dev.eastar.usecase

import android.log.Log
import dev.eastar.entity.GameEntity
import dev.eastar.entity.TryResultEntity
import dev.eastar.repository.GameRepository

class TryNumberUseCase(gameRepository: GameRepository) {
     val entity: GameEntity

    init {
        val answer: Int = gameRepository.generateRandomNumber()
        Log.e("generateRandomNumber answer", answer)
        entity = GameEntity(answer)
        entity.answer = answer
    }

    fun tryNumber(guess: Int): GameEntity {
        Log.e(guess, entity.answer)
        entity.tryResult = TryResultEntity.values()[Integer.signum(guess - entity.answer) + 1]
        entity.tryCount++
        return entity
    }

    fun setPlayers(players: Array<String>) {
        entity.player = players
    }
}