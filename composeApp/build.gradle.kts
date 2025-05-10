import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.googleService)
    alias(libs.plugins.ksp)
    alias(libs.plugins.gradelBuildConfig)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            //COMPOSE
            implementation(compose.preview)
            implementation(libs.accompanist.systemuicontroller)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.splashscreen)

            //FIREBASE
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)

            //KOIN
            implementation(libs.koin.android)

            //KTOR
            implementation(libs.ktor.client.okhttp)
        }

        commonMain.dependencies {
            //MODULES
            implementation(projects.feature.api)
            implementation(projects.shared.ui)
            implementation(projects.shared.utils)
            implementation(projects.shared.resources)
            implementation(projects.network.impl)
            implementation(projects.data.impl)
            implementation(projects.data.api)

            //COMPOSE
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(libs.navigation.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel)

            //KOIN
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewModel)
            implementation(libs.koin.core)

            //KTOR
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.loggin)
            implementation(libs.ktor.serialization.kotlinx.json)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "org.quickness"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.quickness"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        create("release") {
            if (keystorePropertiesFile.exists() && keystoreProperties.containsKey("storeFile")) {
                storeFile = rootProject.file(keystoreProperties["storeFile"].toString())
                storePassword = keystoreProperties["storePassword"].toString()
                keyAlias = keystoreProperties["keyAlias"].toString()
                keyPassword = keystoreProperties["keyPassword"].toString()
            } else {
                println("ADVERTENCIA: keystore.properties o sus entradas requeridas no encontradas. La firma de release podría fallar o usar configuración por defecto.")
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        implementation(libs.androidx.ui.graphics.android)
        implementation(libs.kotlinx.coroutines.core)
        debugImplementation(compose.uiTooling)
    }
}

buildConfig {
    packageName("org.quickness")
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").reader())

    listOf(
        properties.getProperty("TOKENS_API_LINK") to "TOKENS_API_LINK",
        properties.getProperty("AUTH_API_LINK") to "AUTH_API_LINK",
        properties.getProperty("REGISTER_API_LINK") to "REGISTER_API_LINK"
    ).forEach { (value, name) ->
        buildConfigField(
            name = name,
            value = value
        )
    }
}

tasks.register("generateResourceEnum") {
    doLast {
        val resources = mutableListOf<Pair<String, String>>()

        // Leer drawables y crear pares (clave, recurso)
        file("src/commonMain/composeResources/drawable").takeIf { it.exists() }?.listFiles()
            ?.forEach { file ->
                val name = file.nameWithoutExtension
                val normalizedName = name.replace("-", "_")
                resources.add(
                    normalizedName.uppercase() to normalizedName // Pair(key, res)
                )
            }

        // Generar ResourceKey.kt
        val output = """
            package org.quickness
            import quickness.composeapp.generated.resources.Res
            import org.jetbrains.compose.resources.DrawableResource
            import quickness.composeapp.generated.resources.*
            
            enum class ResourceKey(
                val drawable: DrawableResource
            ) {
                ${resources.joinToString(",\n") { (key, res) -> "$key(Res.drawable.$res)" }}
            }
        """.trimIndent()

        val outputFile = file("src/commonMain/kotlin/org/quickness/ResourceKey.kt")
        outputFile.parentFile.mkdirs()
        outputFile.writeText(output)
    }
}

tasks.register("generateResourceNameEnum") {
    doLast {
        val resources = mutableListOf<Pair<String, String>>()

        // Leer drawables y crear pares (clave, recurso)
        file("src/commonMain/composeResources/drawable").takeIf { it.exists() }?.listFiles()
            ?.forEach { file ->
                val name = file.nameWithoutExtension
                val normalizedName = name.replace("-", "_")
                resources.add(
                    normalizedName.uppercase() to normalizedName // Pair(key, res)
                )
            }

        // Generar ResourceKey.kt
        val output = """
            package org.quickness
            import quickness.composeapp.generated.resources.Res
            import org.jetbrains.compose.resources.DrawableResource
            import quickness.composeapp.generated.resources.*
            
            enum class ResourceKey(
                val drawable: DrawableResource
            ) {
                ${resources.joinToString(",\n") { (key, res) -> key }}
            }
        """.trimIndent()

        val outputFile = file("src/commonMain/kotlin/org/quickness/ResourceNameKey.kt")
        outputFile.parentFile.mkdirs()
        outputFile.writeText(output)
    }
}