// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(
        libs.plugins.android.library,
    ) apply false
    alias(
        libs.plugins.android.kotlin,
    ) apply false
    alias(
        libs.plugins.android.hilt,
    ) apply false
    alias(
        libs.plugins.android.kapt,
    ) apply false
    alias(
        libs.plugins.android.ksp,
    ) apply false
    alias(
        libs.plugins.compose.compiler,
    ) apply false
    alias(
        libs
            .plugins
            .version
            .catalog
            .update,
    )
    alias(
        libs.plugins.gradle.versions,
    )
    // alias(libs.plugins.spotless)
    alias(
        libs.plugins.detekt,
    )
    alias(
        libs.plugins.ktlint,
    )
}

apply(
    from = "$rootDir/gradle/gradle-update.gradle",
)
// apply(from = "$rootDir/gradle/spotless.gradle")

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(
        "$projectDir/config/detekt.yml",
    )
    baseline =
        file(
            "$projectDir/config/baseline.xml",
        )
}

// ktlint {
//    debug.set(true)
//    verbose.set(
//        true,
//    )
//    android.set(
//        false,
//    )
//    outputToConsole.set(
//        true,
//    )
//    outputColorName.set(
//        "RED",
//    )
//    ignoreFailures.set(
//        true,
//    )
//    enableExperimentalRules.set(
//        true,
//    )
//    additionalEditorconfig.set( // not supported until ktlint 0.49
//        mapOf(
//            "max_line_length" to "20",
//        ),
//    )
//    baseline.set(
//        file(
//            "my-project-ktlint-baseline.xml",
//        ),
//    )
//    reporters {
//        reporter(
//            ReporterType.PLAIN,
//        )
//        reporter(
//            ReporterType.CHECKSTYLE,
//        )
//    }
//    kotlinScriptAdditionalPaths {
//        include(
//            fileTree(
//                "scripts/",
//            ),
//        )
//    }
//    filter {
//        exclude(
//            "**/generated/**",
//        )
//        include(
//            "**/kotlin/**",
//        )
//    }
// }

ktlint {
    enableExperimentalRules.set(false)

    // Set up other general Ktlint settings (optional)
    android.set(true) // Applies Android-specific formatting
    coloredOutput.set(true) // Colorful output for readability
    verbose.set(true) // Detailed output for debugging
}
