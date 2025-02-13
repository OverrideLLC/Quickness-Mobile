import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    /* COMPOSE */
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    /* UTILS */ alias(libs.plugins.kotlinxSerialization)
    /* GOOGLE SERVICE */ alias(libs.plugins.googleService)
    /* KSP */ alias(libs.plugins.ksp)
    /* ROOM */ alias(libs.plugins.androidxRoom)
    /* BUILDCONFIG */ alias(libs.plugins.gradelBuildConfig)
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
        androidMain {
            kotlin.srcDir("androidMain/kotlin")
        }
        androidMain.dependencies {
            /* COMPOSE */
            implementation(compose.preview)
            implementation(libs.accompanist.systemuicontroller)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.splashscreen)
            implementation(libs.core)

            /* FIREBASE */
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)

            /* KTOR/KOIN */
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)

            /* MAPS */
            implementation(libs.maps.compose)
            implementation(libs.play.services.location)
            implementation(libs.play.services.maps)

            /* UTILS */
            implementation(libs.androidx.biometric)
            implementation(libs.androidx.work.runtime.ktx)
        }

        commonMain.dependencies {
            /* MODULES */
            implementation(projects.feature)
            implementation(projects.data)
            implementation(projects.network)
            implementation(projects.services)
            implementation(projects.shared)
            implementation(projects.services.lyra)

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

            /* KOIN */
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewModel)
            implementation(libs.koin.core)

            /* UTILS */
            implementation(libs.kotlinx.datetime)
            implementation(libs.krypto)
            implementation(libs.qr.kit)
            api(libs.moko.permissions)
            api(libs.moko.permissions.compose)

            /* KTOR */
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.loggin)
            implementation(libs.ktor.serialization.kotlinx.json)

            /* ROOM/DATASTORE */
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqliteBundled)
            implementation(libs.datastore.preference)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
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
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspAndroid", libs.androidx.room.compailer)
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