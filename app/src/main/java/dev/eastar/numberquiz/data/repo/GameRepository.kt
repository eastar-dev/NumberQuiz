package dev.eastar.numberquiz.data.repo

interface GameRepository {
    fun generateRandomNumber(): Int
}
