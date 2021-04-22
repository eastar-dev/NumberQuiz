package _demo

import org.junit.Assert
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

//https://beomseok95.tistory.com/303#JUnit_5_%EC%8B%9C%EC%9E%91%ED%95%98%EA%B8%B0
@Disabled
class FirstJUnit5Test {
    //    @Fast
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    fun testWithExplicitLocalMethodSource(argument: String) {
        assertNotNull(argument)
    }

    companion object {
        @JvmStatic
        fun stringProvider(): Stream<String> = listOf("apple", "banana").stream()
    }

}
