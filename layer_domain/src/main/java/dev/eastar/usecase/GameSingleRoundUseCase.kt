package dev.eastar.usecase

import android.log.Log
import dev.eastar.entity.GameSingleEntity
import dev.eastar.entity.Guess
import dev.eastar.entity.RoundResultEntity
import dev.eastar.repository.GameRepository

class GameSingleRoundUseCase(private val gameRepository: GameRepository) {
    operator fun invoke(guess: Guess): GameSingleEntity {
        val entity: GameSingleEntity = gameRepository.getSingleGame()
        //Log.e(guess, entity.answer)
        val roundResult = RoundResultEntity.values()[Integer.signum(guess - entity.answer) + 1]
        entity.roundResult = roundResult
        entity.roundCount++
        if (roundResult == RoundResultEntity.CORRECT)
            entity.isEndGame = true

        return entity
    }
}

