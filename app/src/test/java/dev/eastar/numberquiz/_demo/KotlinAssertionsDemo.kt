package dev.eastar.numberquiz._demo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.Duration
import kotlin.time.ExperimentalTime

class KotlinAssertionsDemo {

    private val person = Person("Jane", "Doe")
    private val people = setOf(person, Person("John", "Doe"))

    @Test
    fun `exception absence testing`() {
        val calculator = Calculator()
        val result = assertDoesNotThrow("Should not throw an exception") {
            calculator.divide(0, 1)
        }
        assertEquals(0, result)
    }

    @Test
    fun `expected exception testing`() {
        val calculator = Calculator()
        val exception =
            assertThrows(
                ArithmeticException::class.java,
                { calculator.divide(1, 0) }) { "Should throw an exception" }
        assertEquals("/ by zero", exception.message)
    }

    @Test
    fun `grouped assertions`() {
        assertAll("Person properties",
            { assertEquals("Jane", person.getFirstName()) },
            { assertEquals("Doe", person.getLastName()) }
        )
    }

    @Test
    fun `grouped assertions from a stream`() {
        assertAll("People with first name starting with J",
            people
                .stream()
                .map {
                    // This mapping returns Stream<() -> Unit>
                    { assertTrue(it.getFirstName().startsWith("J")) }
                }
        )
    }

    @Test
    fun `grouped assertions from a collection`() {
        assertAll("People with last name of Doe",
            people.map { { assertEquals("Doe", it.getLastName()) } }
        )
    }

    @ExperimentalTime
    @Test
    fun `timeout not exceeded testing`() {
        val fibonacciCalculator = FibonacciCalculator()
        val result: Int = assertTimeout<Int>(Duration.ofMillis(100)) {
            fibonacciCalculator.fib(14)
        }
        assertEquals(377, result)
    }

    @ExperimentalTime
    @Test
    fun `timeout exceeded with preemptive termination`() {
        // The following assertion fails with an error message similar to:
        // execution timed out after 10 ms
        assertTimeoutPreemptively(Duration.ofMillis(10)) {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100)
        }
    }
}

class FibonacciCalculator {
    fun fib(i: Int): Int {
        return 0
    }

}
