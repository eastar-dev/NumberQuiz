package dev.eastar.domain

import android.log.Log
import androidx.annotation.VisibleForTesting

class GameDomain(val answer: Int) {
    private var tryCount: Int = 0

    fun tryNumber(guess: Int): GameResult {
        Log.e(guess, answer)

        val result = signumTest(guess, answer)
        val lowHigh = GameResult.values()[result + 1]
        tryCount++
        return lowHigh

    }

    @VisibleForTesting
    fun signumTest(guess: Int, answer: Int): Int {
        return Integer.signum(guess - answer)
    }
}