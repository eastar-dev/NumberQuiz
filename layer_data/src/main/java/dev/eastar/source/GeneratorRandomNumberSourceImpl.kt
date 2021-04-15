package dev.eastar.source

import kotlin.random.Random

class GeneratorRandomNumberSourceImpl : GeneratorRandomNumberSource {
    override fun getRandomNumber1between100(): Int {
        return Random.nextInt(0, 100) + 1
    }
}