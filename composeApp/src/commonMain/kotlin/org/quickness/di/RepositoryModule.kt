package org.quickness.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.quickness.data.repository.AuthRepositoryImpl
import org.quickness.data.repository.DataStoreRepositoryImpl
import org.quickness.data.repository.RegisterRepositoryImpl
import org.quickness.data.repository.TokenDatabaseRepositoryImpl
import org.quickness.data.repository.TokensRepositoryImpl
import org.quickness.interfaces.repository.AuthRepository
import org.quickness.interfaces.repository.DataStoreRepository
import org.quickness.interfaces.repository.TokenDatabaseRepository
import org.quickness.interfaces.repository.TokensRepository

/**
 * Módulo de Koin que proporciona las dependencias de repositorios de la aplicación.
 *
 * Este módulo registra las instancias de repositorios que manejan la lógica de acceso a los datos
 * relacionados con el inicio de sesión, registro de usuarios y obtención de tokens.
 */
val repositoryModule = module {
    factory<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    factoryOf(::RegisterRepositoryImpl)

    factory<TokensRepository> { TokensRepositoryImpl(get()) }

    factory<TokenDatabaseRepository> { TokenDatabaseRepositoryImpl(get()) }

    factory<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
}
