package io.github.yoanngalentin.app.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import io.kotest.core.spec.style.FreeSpec
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

class CqrsKonsistTest :
    FreeSpec({
        val controllerClasses =
            Konsist
                .scopeFromPackage(packagee = "io.github.yoanngalentin.app.controller", sourceSetName = "main")
                .classes()

        "all controllers" - {
            "should have a RestController annotation" {
                controllerClasses
                    .assertTrue {
                        it.hasAnnotationOf(RestController::class)
                    }
            }

            "controller should have a QueryCommandController or CommandController suffix" {
                controllerClasses
                    .assertTrue {
                        it.name.endsWith("CommandController") || it.name.endsWith("QueryController")
                    }
            }
        }

        "command" - {
            "CommandController should have only PostMapping, PutMapping and DeleteMapping annotation" {
                controllerClasses
                    .filter {
                        it.name.endsWith("CommandController")
                    }.assertTrue(testName = this.testCase.name.testName) {
                        it.hasAllFunctions {
                            println(it.name)
                            it.hasAnnotationOf(PostMapping::class, PutMapping::class, DeleteMapping::class)
                        }
                    }
            }
        }

        "query" - {
            "QueryController should have only PostMapping, PutMapping and DeleteMapping annotation" {
                controllerClasses
                    .filter {
                        it.name.endsWith("QueryController")
                    }.assertTrue(testName = this.testCase.name.testName) {
                        it.hasAllFunctions {
                            println(it.name)
                            it.hasAnnotationOf(GetMapping::class)
                        }
                    }
            }
        }
    })
