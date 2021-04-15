package dev.eastar.repository

import dev.eastar.source.GeneratorRandomNumberSource

class GameRepositoryImpl(private val generatorRandomNumberSource: GeneratorRandomNumberSource) :
    GameRepository {
    override fun generateRandomNumber(): Int {
        return generatorRandomNumberSource.getRandomNumber1between100()
    }
}