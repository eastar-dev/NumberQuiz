package dev.eastar.source

import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

class GeneratorRandomNumberSourceImplTest {

    @RepeatedTest(100)
    fun getRandomNumber1between100() {
        //given
        val generatorRandomNumberSource = GeneratorRandomNumberSourceImpl()
        //when
        val actual: Int = generatorRandomNumberSource.getRandomNumber1between100()
        //then
        assertTrue(actual in 1..100)
    }

    @Test
    fun getManyRandomNumberMin1Max100() {
        //given
        val generatorRandomNumberSource = GeneratorRandomNumberSourceImpl()
        //when
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        repeat(100000) {
            val actual: Int = generatorRandomNumberSource.getRandomNumber1between100()
            min = Integer.min(min, actual)
            max = Integer.max(max, actual)
        }
        //then
        MatcherAssert.assertThat(min, Is.`is`(1))
        MatcherAssert.assertThat(max, Is.`is`(100))
    }
}