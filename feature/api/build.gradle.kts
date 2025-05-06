plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "FeatureApiKit"
            isStatic = true
        }
    }
    androidLibrary {
        namespace = "com.feature.api"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withHostTestBuilder {}

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        commonMain.dependencies {
            //UTILS
            implementation(libs.kotlin.stdlib)

            //MODULES
            //-----Features
            implementation(projects.feature.start)
            implementation(projects.feature.login)
            implementation(projects.feature.register)
            implementation(projects.feature.home)
            implementation(projects.feature.home.settings)
            implementation(projects.feature.home.qr)
            implementation(projects.feature.home.service)
            implementation(projects.feature.home.shop)
            implementation(projects.feature.home.cam)
            //-----Shared
            implementation(projects.shared.ui)
            implementation(projects.shared.utils)
            implementation(projects.shared.resources)
            //-----Network
            implementation(projects.network.api)
            //-----Data
            implementation(projects.data.api)

            //KOIN
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewModel)
            implementation(libs.koin.core)

            //COMPOSE
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(libs.navigation.compose)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.junit)
            }
        }
    }
}