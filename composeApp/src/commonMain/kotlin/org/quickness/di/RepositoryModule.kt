package org.quickness.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.quickness.data.repository.AuthRepositoryImpl
import org.quickness.data.repository.DataStoreRepositoryImpl
import org.quickness.data.repository.RegisterRepositoryImpl
import org.quickness.data.repository.TokenDatabaseRepositoryImpl
import org.quickness.data.repository.TokensRepositoryImpl
import org.quickness.interfaces.repository.DataStoreRepository
import org.quickness.interfaces.repository.TokenDatabaseRepository

/**
 * Módulo de Koin que proporciona las dependencias de repositorios de la aplicación.
 *
 * Este módulo registra las instancias de repositorios que manejan la lógica de acceso a los datos
 * relacionados con el inicio de sesión, registro de usuarios y obtención de tokens.
 */
val repositoryModule = module {
    factoryOf(::AuthRepositoryImpl)

    factoryOf(::RegisterRepositoryImpl)

    factoryOf(::TokensRepositoryImpl)

    factory<TokenDatabaseRepository> { TokenDatabaseRepositoryImpl(get()) }

    factoryOf(::DataStoreRepositoryImpl)
}
