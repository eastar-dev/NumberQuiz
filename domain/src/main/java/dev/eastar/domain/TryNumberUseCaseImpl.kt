package dev.eastar.domain

import android.log.Log
import dev.eastar.entity.GameEntity
import dev.eastar.entity.GameResultEntity
import dev.eastar.repository.GameRepository

class TryNumberUseCaseImpl(gameRepository: GameRepository) : TryNumberUseCase {
    private val entity: GameEntity

    init {
        val answer: Int = gameRepository.generateRandomNumber()
        Log.e("generateRandomNumber answer", answer)
        entity = GameEntity(answer)
        entity.answer = answer
    }

    override fun tryNumber(guess: Int): GameEntity {
        Log.e(guess, entity.answer)
        entity.gameResult = GameResultEntity.values()[Integer.signum(guess - entity.answer) + 1]
        entity.tryCount++
        return entity
    }
}