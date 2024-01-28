plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.android.kapt)
    alias(libs.plugins.android.ksp)
    alias(libs.plugins.android.hilt)
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
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
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
//    packagingOptions { TODO K remove?
//        resources {
//            excludes += '/META-INF/{AL2.0,LGPL2.1}'
//        }
//    }
}

dependencies {

    implementation(libs.androidx.ktx)
    implementation(libs.lifecycle)

//    Camera
    implementation (libs.androidx.camera.view)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.camera2)

//    Hilt
    implementation (libs.hilt)
    kapt(libs.hiltKapt)

//    Room
    implementation (libs.android.room.ktx)
    implementation (libs.android.room.runtime)
    ksp(libs.android.roomKapt)

//    Compose
    implementation (libs.androidx.compose.navigation)
    implementation (libs.androidx.compose.hiltNavigation)
    implementation (libs.androidx.compose.activity)
    implementation (libs.androidx.compose.ui)
    implementation (libs.androidx.compose.graphics)
    implementation (libs.androidx.compose.material)
    implementation (libs.androidx.compose.preview)
    implementation(platform(libs.androidx.compose.composeBom))


//   Tests
    testImplementation (libs.junit.core)

    androidTestImplementation (libs.junit.ext)
    androidTestImplementation (libs.expresso.core)
    androidTestImplementation (libs.junit.rules)
    androidTestImplementation (libs.junit.compose.test)
    androidTestImplementation (libs.navigation.test)

    debugImplementation (libs.compose.test.manifest)
    debugImplementation (libs.compose.test.tooling)
}