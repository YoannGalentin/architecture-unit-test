package io.github.yoanngalentin.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DummyAppApplication

fun main(args: Array<String>) {
    runApplication<DummyAppApplication>(*args)
}
