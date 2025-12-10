package io.github.yoanngalentin.app.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertFalse
import io.kotest.core.spec.style.FreeSpec

class ReactiveKonsistTest :
    FreeSpec({
        // Declaration assertion
        Konsist
            .scopeFromSourceSet("test")
            .classes()
            .forEach { classe ->
                classe
                    .functions()
                    .forEach { func ->
                        "$classe - ${func.name} should not block".config(enabled = true) {
                            func.assertFalse(testName = this.testCase.name.testName) { it.hasTextContaining(".block(") }
                        }
                    }
            }
    })
