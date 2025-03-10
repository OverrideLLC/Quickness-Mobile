plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidxRoom)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    androidLibrary {
        namespace = "com.data.impl"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withHostTestBuilder {}

        withDeviceTestBuilder { sourceSetTreeName = "test" }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    val xcfName = "data:implKit"

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
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                //MODULES
                implementation(projects.shared.utils)
                api(projects.data.api)//se tiene que modificar cuando los modulos feature esten listos

                //DATABASE
                implementation(libs.androidx.room.runtime)
                implementation(libs.androidx.sqliteBundled)
                implementation(libs.datastore.preference)

                //KOIN
                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewModel)
                implementation(libs.koin.core)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            kotlin.srcDir("androidMain/kotlin")
            dependencies{
                //KOIN
                implementation(libs.koin.android)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.junit)
            }
        }

        iosMain {
            dependencies {
                // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
                // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
                // part of KMP’s default source set hierarchy. Note that this source set depends
                // on common by default and will correctly pull the iOS artifacts of any
                // KMP dependencies declared in commonMain.
            }
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspAndroid", libs.androidx.room.compailer)
}