package junit5demo

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@Disabled
class ArgumentsSourceTest {
    @ParameterizedTest
    @ArgumentsSource(MyArgumentsProvider::class)
    fun testWithArgumentsSource(argument: String) {
        assertNotNull(argument)
    }

    class MyArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext): Stream<Arguments> {
            return listOf("apple", "banana").map { Arguments.of(it) }.stream()
        }
    }
}