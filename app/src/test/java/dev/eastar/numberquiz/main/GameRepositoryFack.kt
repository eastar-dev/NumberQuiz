package dev.eastar.numberquiz.main

import dev.eastar.numberquiz.data.repo.GameRepository

class GameRepositoryFack : GameRepository {
    override fun generateRandomNumber() = 5
}
