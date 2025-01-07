package org.quickness.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.quickness.data.room.dao.TokenDao
import org.quickness.data.room.db.TokenDatabase
import org.quickness.data.room.localdb.CreateDatabase

/**
 * Módulo de Koin que define las dependencias de la aplicación.
 */
val appModule: Module = module {
    singleOf(::createHttpClient)
    single<TokenDatabase> { CreateDatabase(get()).getTokenDatabase() }
    single<TokenDao> { get<TokenDatabase>().tokenDao() }
}

/**
 * Crea un cliente HTTP configurado con los plugins y opciones necesarias.
 *
 * @param engine Motor de cliente HTTP utilizado para crear la instancia.
 * @return Instancia de [HttpClient] configurada.
 */
fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        installLogging()
        installContentNegotiation()
    }
}

/**
 * Configura el plugin de logging del cliente HTTP.
 */
private fun HttpClientConfig<*>.installLogging() {
    install(Logging) {
        level = LogLevel.ALL // Muestra todos los niveles de log para la depuración
    }
}

/**
 * Configura el plugin de negociación de contenido para el cliente HTTP.
 */
private fun HttpClientConfig<*>.installContentNegotiation() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true // Formateo legible de la salida JSON
                isLenient = true // Permite la tolerancia a errores en el parsing de JSON
                ignoreUnknownKeys = true // Ignora claves desconocidas en la deserialización
            }
        )
    }
}

/**
 * Inicializa el contenedor de dependencias de Koin con los módulos especificados.
 *
 * @param appDeclaration Declaración de la configuración de la aplicación (opcional).
 */
fun initKoin(appDeclaration: KoinAppDeclaration? = null) =
    startKoin {
        appDeclaration?.invoke(this)
        modules(
            appModule,
            dataModule,
            repositoryModule,
            viewModelsHome,
            viewModelsStart,
            serviceModule,
            NativeModule,
            firebaseModule
        )
    }
