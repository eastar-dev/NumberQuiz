package dev.eastar.entity

class GameMultiEntity(var answer: Answer)  {
    lateinit var roundResult: RoundResultEntity
     var winner: String? = null
    var isEndGame: Boolean = false
}