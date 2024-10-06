// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.android.hilt) apply false
    alias(libs.plugins.android.kapt) apply false
    alias(libs.plugins.android.ksp) apply false
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
}

apply(from = "$rootDir/gradle/gradle-update.gradle")
apply(from = "$rootDir/gradle/spotless.gradle")

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/config/detekt.yml")
    baseline = file("$projectDir/config/baseline.xml")
}
