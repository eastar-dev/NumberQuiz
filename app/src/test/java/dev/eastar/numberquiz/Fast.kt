package dev.eastar.numberquiz

import org.junit.jupiter.api.Tag

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Tag("fast")
annotation class Fast