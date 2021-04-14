package dev.eastar.domain

import android.log.Log
import dev.eastar.enty.GameResult

class GameDomain(private val answer: Int) {
    var player: Array<String> = emptyArray()

    var tryCount: Int = 0
        get() = field
        private set
    val winner: String
        get() = player[tryCount / player.size]


    fun tryNumber(guess: Int): GameResult {
        Log.e(guess, answer)
        val lowHigh = GameResult.values()[Integer.signum(guess - answer) + 1]
        tryCount++
        return lowHigh
    }
}