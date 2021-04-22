package dev.eastar.usecase

import dev.eastar.entity.GameEntity
import dev.eastar.entity.Guess
import dev.eastar.entity.RoundResult

class RoundResultUseCase {
    operator fun invoke(game: GameEntity, guess: Guess) {
        game.roundResult = RoundResult.values()[Integer.signum(guess - game.answer) + 1]
    }
}

