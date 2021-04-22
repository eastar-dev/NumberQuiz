package dev.eastar.entity

open class GameEntity(var answer: Answer) {
    lateinit var roundResult: RoundResult
    var isEndGame: Boolean = false
    var roundCount: Int = 0
}