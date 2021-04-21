package dev.eastar.entity

class GameSingleEntity(var answer: Answer) {
    lateinit var roundResult: RoundResultEntity
    var isEndGame: Boolean = false
    var roundCount: Int = 0
}