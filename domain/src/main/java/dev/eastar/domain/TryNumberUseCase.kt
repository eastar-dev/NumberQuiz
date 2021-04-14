package dev.eastar.domain

import dev.eastar.enty.GameResult

//그리고 useCase 는 interface 가 아니라 그냥 구현체
interface TryNumberUseCase {
    var player: Array<String>
    var tryCount: Int
    val winner: String
    fun tryNumber(guess: Int): GameResult
}