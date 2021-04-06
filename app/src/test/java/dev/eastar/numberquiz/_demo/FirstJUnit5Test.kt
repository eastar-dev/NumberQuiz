package dev.eastar.numberquiz._demo

import org.junit.Assert
import org.junit.Test

//https://beomseok95.tistory.com/303#JUnit_5_%EC%8B%9C%EC%9E%91%ED%95%98%EA%B8%B0
class FirstJUnit5Test {
    @Fast
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }
}

