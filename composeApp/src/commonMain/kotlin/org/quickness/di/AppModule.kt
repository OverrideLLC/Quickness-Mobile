package org.quickness.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.quickness.QRCodeGeneratorImpl
import org.quickness.SharedPreference
import org.quickness.interfaces.QRCodeGenerator

val appModule: Module = module {
    single { crateHttpClient(get()) }
    single<QRCodeGenerator> { QRCodeGeneratorImpl() }
    single { SharedPreference() }
}

fun crateHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}

fun initKoin(appDeclaration: KoinAppDeclaration? = null) =
    startKoin {
        appDeclaration?.invoke(this)
        modules(
            appModule,
            dataModule,
            repositoryModule,
            viewModelsModule,
            networkModule,
            NativeModule,
            firebaseModule
        )
    }