package dev.eastar.domain

import android.log.Log

class GameDomain(private val answer: Int, private val player: Array<String> = emptyArray()) {
    private var tryCount: Int = 0
    private val winner: String
        get() = player[tryCount / player.size]


    fun tryNumber(guess: Int): GameResult {
        Log.e(guess, answer)
        val result = signumTest(guess, answer)
        val lowHigh = GameResult.values()[result + 1]
        tryCount++
        return lowHigh
    }

    private fun signumTest(guess: Int, answer: Int): Int = Integer.signum(guess - answer)
}