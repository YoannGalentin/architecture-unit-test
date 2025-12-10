package io.github.yoanngalentin.app.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.verify.assertTrue
import io.kotest.core.spec.style.FreeSpec
import org.springframework.data.repository.reactive.ReactiveCrudRepository

class HexagonalKonsistTest :
    FreeSpec({
        val applicationBasePackageName = "io.github.yoanngalentin.app"

        "hexagonal architecture layers have correct dependencies".config(enabled = true) {
            Konsist
                .scopeFromProduction()
                .assertArchitecture {
                    // Define layers
                    val domain = Layer("Domain", "$applicationBasePackageName.domain..")
                    val presentation = Layer("Presentation", "$applicationBasePackageName.controller..")
                    val data = Layer("Data", "$applicationBasePackageName.data..")

                    // Define architecture assertions
                    domain.dependsOnNothing()

                    presentation.dependsOn(domain)
                    presentation.doesNotDependOn(data)

                    data.dependsOn(domain)
                    data.doesNotDependOn(presentation)
                }
        }

        "interfaces with 'Repository' annotation should reside in 'data' package".config(enabled = false) {
            Konsist
                .scopeFromProduction()
                .interfaces()
                .withAnnotationOf(ReactiveCrudRepository::class)
                .assertTrue { it.resideInPackage("..data..") }
        }

        "should only have domain, data and presentation packages".config(enabled = false) {
            Konsist
                .scopeFromProduction()
                .packages
                .assertTrue { packagee ->
                    println("package ${packagee.name}")
                    listOf(
                        applicationBasePackageName,
                        "$applicationBasePackageName.domain",
                        "$applicationBasePackageName.data",
                        "$applicationBasePackageName.controller",
                    ).any {
                        packagee.name == it
                    }
                }
        }
    })
