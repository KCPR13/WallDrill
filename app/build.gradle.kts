plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.android.kapt)
    alias(libs.plugins.android.ksp)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.spotless)
    alias(libs.plugins.ktlint)
}

android {

    val appId = "pl.kacper.misterski.walldrill"

    namespace = appId
    compileSdk = libs.versions.sdk.get().toInt()

    defaultConfig {
        applicationId = appId
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.sdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments +=
                    mapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                    )
            }
        }
    }

    sourceSets {
        getByName("androidTest") {
            res.srcDirs("src/res")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        getByName("debug") {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
    packaging { // TODO K remove?
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":openCV"))

    implementation(libs.androidx.ktx)
    implementation(libs.lifecycle)

//    Camera
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.camera2)

//    Hilt
    implementation(libs.hilt)
    kapt(libs.hiltKapt)

//    Room
    implementation(libs.android.room.ktx)
    implementation(libs.android.room.runtime)
    ksp(libs.android.roomKapt)

//    Compose
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.hiltNavigation)
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.graphics)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.preview)
    implementation(platform(libs.androidx.compose.composeBom))

//   Tests
    testImplementation(libs.junit.core)

    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.expresso.core)
    androidTestImplementation(libs.junit.rules)
    androidTestImplementation(libs.junit.compose.test)
    androidTestImplementation(libs.navigation.test)

    debugImplementation(libs.compose.test.manifest)
    debugImplementation(libs.compose.test.tooling)
}

// TODO K move to separate file
spotless {

    val ktlintVersion = "0.48.0"
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt")
        ktlint(ktlintVersion)
            .editorConfigOverride(
                mapOf(
                    "ktlint_code_style" to "android",
                    "ij_kotlin_allow_trailing_comma" to true,
                    // These rules were introduced in ktlint 0.46.0 and should not be
                    // enabled without further discussion. They are disabled for now.
                    // See: https://github.com/pinterest/ktlint/releases/tag/0.46.0
                    "disabled_rules" to
                        "filename," +
                        "annotation,annotation-spacing," +
                        "argument-list-wrapping," +
                        "double-colon-spacing," +
                        "enum-entry-name-case," +
                        "multiline-if-else," +
                        "experimental:function-naming," +
                        "no-empty-first-line-in-method-block," +
                        "package-name," +
                        "trailing-comma," +
                        "spacing-around-angle-brackets," +
                        "function-start-of-body-spacing," +
                        "spacing-between-declarations-with-annotations," +
                        "spacing-between-declarations-with-comments," +
                        "unary-op-spacing",
                ),
            )
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
        licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
    }
    format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
    }
}
