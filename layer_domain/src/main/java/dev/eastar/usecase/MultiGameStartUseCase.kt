package dev.eastar.usecase

import dev.eastar.entity.GameEntity
import dev.eastar.entity.GameMulti
import dev.eastar.entity.Player
import dev.eastar.repository.GameRepository

class MultiGameStartUseCase(private val gameRepository: GameRepository) {
    operator fun invoke(players: Array<Player>) {
        val answer = gameRepository.generateRandomNumber()
        val game: GameEntity = GameMulti(answer, players)
        gameRepository.setGame(game)
    }
}

