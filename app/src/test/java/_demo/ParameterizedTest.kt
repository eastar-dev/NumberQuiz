package _demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@Disabled
class ParameterizedTestDemo {
    @ParameterizedTest
    @ValueSource(strings = ["racecar", "radar", "able was I ere I saw elba"])
    fun palindromes(candidate: String) {
        Assertions.assertTrue(candidate.isNotEmpty())
    }
}