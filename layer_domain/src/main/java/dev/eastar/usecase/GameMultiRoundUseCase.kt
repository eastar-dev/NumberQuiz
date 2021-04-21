package dev.eastar.usecase

import dev.eastar.entity.GameMultiEntity
import dev.eastar.entity.Guess
import dev.eastar.entity.Player
import dev.eastar.entity.RoundResultEntity
import dev.eastar.repository.GameRepository

class GameMultiRoundUseCase(private val gameRepository: GameRepository) {
    operator fun invoke(guess: Guess, name: Player): GameMultiEntity {
        val entity: GameMultiEntity = gameRepository.getMultiGame()
        entity.roundResult = RoundResultEntity.values()[Integer.signum(guess - entity.answer) + 1]
        if (entity.roundResult == RoundResultEntity.CORRECT) {
            entity.isEndGame = true
            entity.winner = name
        }
        return entity
    }
}

