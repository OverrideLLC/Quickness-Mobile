package org.quickness.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.quickness.data.repository.AuthRepositoryImpl
import org.quickness.data.repository.RegisterRepositoryImpl
import org.quickness.data.repository.TokenDatabaseRepositoryImpl
import org.quickness.data.repository.TokensRepositoryImpl
import org.quickness.interfaces.repository.TokenDatabaseRepository

/**
 * Módulo de Koin que proporciona las dependencias de repositorios de la aplicación.
 *
 * Este módulo registra las instancias de repositorios que manejan la lógica de acceso a los datos
 * relacionados con el inicio de sesión, registro de usuarios y obtención de tokens.
 */
val repositoryModule = module {
    // Registro de LoginRepository como una fábrica, creando una nueva instancia cada vez que se solicite.
    factoryOf(::AuthRepositoryImpl)

    // Registro de RegisterRepository como una fábrica, creando una nueva instancia cada vez que se solicite.
    factoryOf(::RegisterRepositoryImpl)

    // Registro de TokensRepository como una fábrica, creando una nueva instancia cada vez que se solicite.
    factoryOf(::TokensRepositoryImpl)

    factory<TokenDatabaseRepository> { TokenDatabaseRepositoryImpl(get()) }
}
