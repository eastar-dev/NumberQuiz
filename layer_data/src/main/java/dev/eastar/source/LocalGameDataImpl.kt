package dev.eastar.source

import dev.eastar.entity.GameEntity

class LocalGameDataImpl : LocalGameData {
    var gameEntity: GameEntity? = null
    override fun setGame(game: GameEntity) {
        gameEntity = game
    }

    override fun getGame(): GameEntity {
        return gameEntity!!
    }
}