package io.github.yoanngalentin.app

import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
    fromApplication<DummyAppApplication>().with(TestcontainersConfiguration::class).run(*args)
}
