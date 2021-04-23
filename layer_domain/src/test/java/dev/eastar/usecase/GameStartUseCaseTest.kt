package dev.eastar.usecase

import dev.eastar.entity.GameEntity
import dev.eastar.repository.GameRepository
import junit.util.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.isA
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

@DisplayName("멀티게임시작시 ")
internal class GameStartUseCaseTest {

    private lateinit var gameStartUseCase: GameStartUseCase
    private val gameRepository: GameRepository by lazy { mock() }

    @BeforeEach
    fun setUp() {
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        gameStartUseCase = GameStartUseCase(gameRepository)
    }

    @Test
    @DisplayName("answer 값을5로 싱글 게임을 시작한다.")
    operator fun invoke() {
        //given
        val case = gameStartUseCase

        //when
        case()

        //then
        val argumentCaptor: ArgumentCaptor<GameEntity> = argumentCaptor()
        Mockito.verify(gameRepository).setGame(capture(argumentCaptor))
        val actual = argumentCaptor.value
        assertThat(actual, isA(GameEntity::class.java))
        assertThat(actual.answer, isit(5))
    }
}