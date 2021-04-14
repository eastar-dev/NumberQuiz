package dev.eastar.domain

import dev.eastar.entity.GameEntity


//그리고 useCase 는 interface 가 아니라 그냥 구현체
interface TryNumberUseCase {
    fun tryNumber(guess: Int): GameEntity
}