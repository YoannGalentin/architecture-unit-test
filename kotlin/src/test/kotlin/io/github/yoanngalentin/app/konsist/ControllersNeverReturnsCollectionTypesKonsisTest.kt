package io.github.yoanngalentin.app.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.functions
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.verify.assertFalse
import io.kotest.core.spec.style.FreeSpec
import org.springframework.web.bind.annotation.RestController

class ControllersNeverReturnsCollectionTypesKonsisTest :
    FreeSpec({
        "Controllers never returns collection types" {
            Konsist
                .scopeFromPackage("io.github.yoanngalentin.app..")
                .classes()
                .withAnnotationOf(RestController::class)
                .functions()
                .assertFalse { function ->
                    println("function ${function.name} has return type ${function.returnType}")
                    false
                    // Ne fonctionne pas si on utilise une ResponseEntity et List
                    // function.hasReturnType { it.isKotlinCollectionType }
                    // function.hasReturnType { returnType -> listOf("Mono<ResponseEntity<List").any { returnType.hasNameStartingWith(it) } }
                }
        }
    })
