package dev.eastar.domain

import dev.eastar.enty.GameResult

interface TryNumberUseCase {
    var player: Array<String>
    var tryCount: Int
    val winner: String
    fun tryNumber(guess: Int): GameResult
}