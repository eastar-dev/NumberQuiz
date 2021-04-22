package dev.eastar.usecase

import dev.eastar.entity.GameEntity
import dev.eastar.entity.Guess
import dev.eastar.repository.GameRepository

class GameStartUseCase(
    private val gameRepository: GameRepository,
) {
    operator fun invoke() {
        val answer = gameRepository.generateRandomNumber()
        val game = GameEntity(answer)
        gameRepository.setGame(game)
    }
}

