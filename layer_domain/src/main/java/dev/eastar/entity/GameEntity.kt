package dev.eastar.entity

class GameEntity(var answer: Int) {
    var tryCount: Int = 0
    lateinit var tryResult: TryResultEntity
    var player: Array<String> = emptyArray()

    val winner: String
        get() = player[(tryCount - 1) % player.size]

    val isEndGame: Boolean
        get() = tryResult == TryResultEntity.correct
}