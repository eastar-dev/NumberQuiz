package dev.eastar.usecase

import dev.eastar.entity.GameMulti
import dev.eastar.entity.Guess
import dev.eastar.entity.Player
import dev.eastar.repository.GameRepository

class MultiGameRoundUseCase(private val gameRoundUseCase: GameRoundUseCase) {
    operator fun invoke(guess: Guess): GameMulti {
        val game = gameRoundUseCase(guess) as? GameMulti
        game ?: throw Exception("only multi game in hear")
        with(game) {
            if (isEndGame)
                winner = players[(roundCount - 1) % players.size]
        }
        return game
    }
}

