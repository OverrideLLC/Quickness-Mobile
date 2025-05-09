package com.network.impl.di

import com.network.api.repository.ApolloRepository
import com.network.api.repository.AuthRepository
import com.network.api.repository.RegisterRepository
import com.network.api.repository.TokensRepository
import com.network.api.service.AuthUserService
import com.network.api.service.FirebaseAuth
import com.network.api.service.RegisterService
import com.network.api.service.TokensService
import com.network.impl.repository.ApolloRepositoryImpl
import com.network.impl.repository.AuthRepositoryImpl
import com.network.impl.repository.RegisterRepositoryImpl
import com.network.impl.repository.TokensRepositoryImpl
import com.network.impl.service.ApolloServiceImpl
import com.network.impl.service.AuthUserServiceImpl
import com.network.impl.service.FirebaseAuthImpl
import com.network.impl.service.RegisterServiceImpl
import com.network.impl.service.TokensServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin module that provides network-related repository dependencies.
 *
 * This module configures and provides concrete implementations for various network repositories:
 * - [AuthRepository]: Handles authentication-related data operations.
 * - [RegisterRepository]: Manages user registration data operations.
 * - [TokensRepository]: Manages token-related data operations.
 *
 * Each repository is bound to its respective interface, allowing for loose coupling and
 * easy swapping of implementations.
 */
val repositoryNetworkModule = module {
    factoryOf(::AuthRepositoryImpl).bind(AuthRepository::class)
    factoryOf(::RegisterRepositoryImpl).bind(RegisterRepository::class)
    factoryOf(::TokensRepositoryImpl).bind(TokensRepository::class)
    factoryOf(::ApolloRepositoryImpl).bind(ApolloRepository::class)
}

/**
 * Koin module that provides the network-related service dependencies.
 *
 * This module registers the service implementations responsible for handling business logic related to
 * user registration, token management, and user authentication. It binds each implementation to its
 * corresponding service interface, enabling dependency injection.
 */
val serviceModule = module {
    factoryOf(::RegisterServiceImpl).bind(RegisterService::class)
    factoryOf(::TokensServiceImpl).bind(TokensService::class)
    factoryOf(::AuthUserServiceImpl).bind(AuthUserService::class)
    factoryOf(::ApolloServiceImpl).bind(ApolloRepository::class)
    singleOf(::createHttpClient)
}

/**
 * Koin module that provides a singleton instance of [FirebaseAuthImpl].
 *
 * This module configures and provides a single instance of [FirebaseAuthImpl],
 * which is bound to the [FirebaseAuth] interface. This ensures that the same
 * instance is used throughout the application for Firebase authentication operations.
 */
val firebaseModule: Module get() = module { singleOf(::FirebaseAuthImpl).bind(FirebaseAuth::class) }

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
        logger = Logger.DEFAULT
        level = LogLevel.ALL
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