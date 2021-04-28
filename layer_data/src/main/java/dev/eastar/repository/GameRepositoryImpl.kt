package dev.eastar.repository

import dev.eastar.entity.GameEntity
import dev.eastar.source.GeneratorRandomNumberSource
import dev.eastar.source.LocalGameData

class GameRepositoryImpl(
    private val generatorRandomNumberSource: GeneratorRandomNumberSource,
    private val localGameData: LocalGameData
) : GameRepository {
    override fun generateRandomNumber(): Int = generatorRandomNumberSource.getRandomNumber1between100()
    override fun getGame(): GameEntity = localGameData.getGame()
    override fun setGame(game: GameEntity) = localGameData.setGame(game)
}