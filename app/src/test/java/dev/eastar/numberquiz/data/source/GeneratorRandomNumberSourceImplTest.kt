package dev.eastar.numberquiz.data.source

import dev.eastar.numberquiz.data.source.GeneratorRandomNumberSourceImpl
import org.hamcrest.MatcherAssert
import org.hamcrest.collection.IsIn
import org.hamcrest.core.Is
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class GeneratorRandomNumberSourceImplTest {

    @Test
    fun getRandomNumber1between100() {
        //given
        val generatorRandomNumberSource = GeneratorRandomNumberSourceImpl()
        repeat(10000) {
            //when
            val actual: Int = generatorRandomNumberSource.getRandomNumber1between100()
            //then
            MatcherAssert.assertThat(actual, IsIn((0..100).toList()))
        }
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