package _demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@Disabled
class MethodSource {
    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    fun testWithMultiArgMethodSource(str: String, num: Int, list: List<String>) {
        Assertions.assertEquals(5, str.length)
        Assertions.assertTrue(num in 1..2)
        Assertions.assertEquals(2, list.size)
    }


    companion object {
        //jvmTarget = '1.8'
        @JvmStatic
        fun stringIntAndListProvider(): Stream<Arguments> {
            return listOf<Arguments>(
                Arguments.of("apple", 1, listOf("a", "b")),
                Arguments.of("lemon", 2, listOf("x", "y"))
            ).stream()
        }
    }
}