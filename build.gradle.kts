import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

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

apply("${project.rootDir}/scripts/gradle-update.gradle")

subprojects {
	afterEvaluate {
		tasks.named("preBuild") {
			dependsOn("spotlessApply")
		}
	}

	tasks.withType<Detekt>().configureEach {
		jvmTarget = "1.8"
	}
	tasks.withType<DetektCreateBaselineTask>().configureEach {
		jvmTarget = "1.8"
	}

	tasks.withType<Detekt>().configureEach {
		reports {
			xml {
				required.set(true)
				outputLocation.set(file("build/reports/detekt/detekt-report.xml"))
			}
			html {
				required.set(true)
				outputLocation.set(file("build/reports/detekt/detekt-report.html"))
			}
		}
	}
}

detekt {
	buildUponDefaultConfig = true
	allRules = false
	config.setFrom("$projectDir/config/detekt.yml")
	baseline = file("$projectDir/config/baseline.xml")
}
