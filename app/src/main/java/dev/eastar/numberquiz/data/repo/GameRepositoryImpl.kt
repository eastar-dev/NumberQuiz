package dev.eastar.numberquiz.data.repo

import dev.eastar.numberquiz.data.source.GeneratorRandomNumberSource

class GameRepositoryImpl(private val generatorRandomNumberSource: GeneratorRandomNumberSource) :
    GameRepository {
    override fun generateRandomNumber(): Int {
        return generatorRandomNumberSource.getRandomNumber1between100()
    }
}