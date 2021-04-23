package dev.eastar.usecase

import dev.eastar.entity.GameEntity
import dev.eastar.entity.GameMulti
import dev.eastar.repository.GameRepository
import junit.util.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify

@DisplayName("멀티게임시작시 ")
internal class MultiGameStartUseCaseTest {

    private lateinit var multiGameStartUseCase: MultiGameStartUseCase
    private val gameRepository: GameRepository by lazy { mock() }

    @BeforeEach
    fun setUp() {
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)
        multiGameStartUseCase = MultiGameStartUseCase(gameRepository)
    }

    @Test
    @DisplayName("\"성춘향\", \"변사또\"를 player로 게임을 시작한다.")
    operator fun invoke() {
        //given
        val case = multiGameStartUseCase

        //when
        case(arrayOf("성춘향", "변사또"))

        //then
        val argumentCaptor: ArgumentCaptor<GameEntity> = argumentCaptor()
        verify(gameRepository).setGame(capture(argumentCaptor))

        assertThat(argumentCaptor.value, instanceOf(GameMulti::class.java))
        val actual = argumentCaptor.value as GameMulti
        assertThat(actual.players, isit(arrayOf("성춘향", "변사또")))
    }
}