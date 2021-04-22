package dev.eastar.usecase

import android.util.*
import dev.eastar.entity.GameMulti
import dev.eastar.repository.GameRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsCollectionContaining
import org.hamcrest.core.IsCollectionContaining.hasItem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify


internal class MultiGameStartUseCaseTest {

    private lateinit var multiGameStartUseCase: MultiGameStartUseCase
    private val gameRepository: GameRepository by lazy { mock() }

    //val game = GameMulti(5, arrayOf("성춘향", "변사또"))

    @BeforeEach
    fun setUp() {
        whenever(gameRepository.generateRandomNumber()).thenReturn(5)



        multiGameStartUseCase = MultiGameStartUseCase(gameRepository)
    }

    @Test
    operator fun invoke() {
        //given
        val case = multiGameStartUseCase
        //when

        case("성춘향", "변사또")
        val captor = ArgumentCaptor.forClass(GameMulti::class.java)
        verify(gameRepository).setGame(captor.capture())

        //then
        //verify(gameRepository).setGame()
    }


    @Test
    fun argumentCapture() {
        val mockedList: MutableList<String> = mock()

        mockedList.addAll(listOf("someElement"))

        val argumentCaptor: ArgumentCaptor<List<String>> = argumentCaptor()
        verify(mockedList).addAll(capture(argumentCaptor))
        assertThat(argumentCaptor.value, hasItem("someElement"))


        //val date: Date = mock()
        //date.time = 5L
        //val captor = argumentCaptor<Long>()
        //verify(date).time = captor.capture()
        //assertThat(captor.value, isit(5L))

    }
}