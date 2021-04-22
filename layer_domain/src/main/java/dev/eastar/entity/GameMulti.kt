package dev.eastar.entity

class GameMulti(answer: Answer, var players: Array<out String>) : GameEntity(answer) {
    var winner: String? = null
}