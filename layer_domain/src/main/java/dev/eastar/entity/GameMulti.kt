package dev.eastar.entity

class GameMulti(answer: Answer, var players: Array<Player>) : GameEntity(answer) {
    var winner: String? = null
}