package junit5demo

import junit.util.argumentCaptor
import junit.util.capture
import junit.util.isit
import junit.util.mock
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsCollectionContaining.hasItem
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify
import java.util.*

@Disabled
class ArgumentCaptureTest {
    @Test
    fun argumentCapture() {
        val mockedList: MutableList<String> = mock()
        mockedList.addAll(listOf("someElement"))
        val argumentCaptor: ArgumentCaptor<List<String>> = argumentCaptor()
        verify(mockedList).addAll(capture(argumentCaptor))
        assertThat(argumentCaptor.value, hasItem("someElement"))


        val date: Date = mock()
        date.time = 5L
        val argumentCaptor1: ArgumentCaptor<Long> = argumentCaptor()
        verify(date).time = capture(argumentCaptor1)
        assertThat(argumentCaptor1.value, isit(5L))

    }
}