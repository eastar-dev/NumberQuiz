package dev.eastar.usecase

import dev.eastar.entity.GameEntity
import dev.eastar.entity.GameMulti
import dev.eastar.entity.Guess
import dev.eastar.entity.RoundResult
import dev.eastar.repository.GameRepository

class GameRoundUseCase(
    private val gameRepository: GameRepository,
    private val roundResultUseCase: RoundResultUseCase
) {
    operator fun invoke(guess: Guess): GameEntity {
        val game: GameEntity = gameRepository.getGame()
        game.roundCount++
        roundResultUseCase(game, guess)
        game.isEndGame = game.roundResult == RoundResult.CORRECT

        return game
    }
}

