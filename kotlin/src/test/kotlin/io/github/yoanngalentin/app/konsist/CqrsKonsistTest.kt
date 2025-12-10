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
        val controllerClassesScope =
            Konsist
                .scopeFromPackage(packagee = "io.github.yoanngalentin.app.controller", sourceSetName = "main")
                .classes()

        "all controllers".config(enabled = true) - {
            "should have a RestController annotation" {
                controllerClassesScope
                    .assertTrue {
                        it.hasAnnotationOf(RestController::class)
                    }
            }

            "controller should have a QueryCommandController or CommandController suffix" {
                controllerClassesScope
                    .assertTrue {
                        it.name.endsWith("CommandController") || it.name.endsWith("QueryController")
                    }
            }
        }

        "command".config(enabled = true) - {
            "CommandController should have only PostMapping, PutMapping and DeleteMapping annotation" {
                controllerClassesScope
                    .filter {
                        it.name.endsWith("CommandController")
                    }.assertTrue(testName = this.testCase.name.testName) { classe ->
                        classe.hasAllFunctions {
                            println(it.name)
                            it.hasAnnotationOf(PostMapping::class, PutMapping::class, DeleteMapping::class)
                        }
                    }
            }
        }

        "query".config(enabled = true) - {
            "QueryController should have only GetMapping annotation" {
                controllerClassesScope
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
