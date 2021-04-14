package dev.eastar.domain

import android.log.Log
import dev.eastar.enty.GameResult
import dev.eastar.repository.GameRepository

class TryNumberUseCaseImpl(gameRepository: GameRepository) : TryNumberUseCase {

    private val answer: Int = gameRepository.generateRandomNumber()

    init {
        Log.e("generateRandomNumber answer", answer)
    }

    override var player: Array<String> = emptyArray()
    override var tryCount: Int = 0
    override val winner: String
        get() = player[(tryCount - 1) % player.size]

    override fun tryNumber(guess: Int): GameResult {
        Log.e(guess, answer)
        val lowHigh = GameResult.values()[Integer.signum(guess - answer) + 1]
        tryCount++
        return lowHigh
    }
}