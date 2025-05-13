plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.googleService)
}

kotlin {
    androidLibrary {
        namespace = "org.override.quickness.network.impl"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    val xcfName = "NetworkImplKit"

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //MODULES
            implementation(projects.shared.utils)
            implementation(projects.network.api)

            //UTILS
            implementation(libs.kotlin.stdlib)

            //KTOR
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.loggin)
            implementation(libs.ktor.serialization.kotlinx.json)

            //KOIN
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewModel)
            implementation(libs.koin.core)

            //GEMINI
            implementation(libs.generativeai)
            implementation(libs.generativeai.google.wasm.js)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            //FIREBASE
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)

            //KTOR
            implementation(libs.ktor.client.okhttp)

            //KOIN
            implementation(libs.koin.android)
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.junit)
            }
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}