plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.quickness.recurces"
    generateResClass = auto
}

kotlin {
    androidLibrary {
        namespace = "com.recurces"
        minSdk = libs.versions.android.minSdk.get().toInt()
        compileSdk = libs.versions.android.compileSdk.get().toInt()
    }

    val xcfName = "shared:uiKit"

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

                /* COMPOSE */
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.navigation.compose)
                api(compose.components.resources)
            }
        }

        commonTest {
            dependencies {
            }
        }

        androidMain {
            dependencies {
                /* COMPOSE */
                implementation(compose.preview)
                implementation(libs.accompanist.systemuicontroller)
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.core.splashscreen)
                implementation(libs.core)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}