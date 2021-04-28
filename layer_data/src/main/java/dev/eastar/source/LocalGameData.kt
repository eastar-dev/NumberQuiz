package dev.eastar.source

import dev.eastar.entity.GameEntity

interface LocalGameData {
    fun setGame(game: GameEntity)
    fun getGame(): GameEntity
}