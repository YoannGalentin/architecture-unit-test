package io.github.yoanngalentin.app.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import io.kotest.core.spec.style.FreeSpec

class BasicArchitectureKonsistTest :
    FreeSpec({
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("Controller")
            .forEach { clazz ->
                "${clazz.name} should reside in ..controller package".config(enabled = false) {
                    clazz.assertTrue(testName = this.testCase.name.testName) { it.resideInPackage("..controller") }
                }
            }

        "N-tier architecture has correct dependencies".config(enabled = false) {
            Konsist
                .scopeFromSourceSet("main")
                .assertArchitecture {
                    val presentation = Layer("Presentation", "io.github.yoanngalentin.app.controller.")
                    val business = Layer("Business", "io.github.yoanngalentin.app.service..")
                    val database = Layer("Database", "io.github.yoanngalentin.app.data..")

                    presentation.dependsOn(business)
                    presentation.doesNotDependOn(database)

                    business.dependsOn(database)
                    business.doesNotDependOn(presentation)

                    database.doesNotDependOn(business)
                    database.doesNotDependOn(presentation)
                }
        }
    })
