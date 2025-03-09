package org.quickness.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.quickness.data.repository.DataStoreRepositoryImpl
import org.quickness.data.repository.TokenDatabaseRepositoryImpl
import org.quickness.interfaces.repository.data.DataStoreRepository
import org.quickness.interfaces.repository.data.TokenDatabaseRepository

/**
 * Módulo de Koin que proporciona las dependencias de repositorios de la aplicación.
 *
 * Este módulo registra las instancias de repositorios que manejan la lógica de acceso a los datos
 * relacionados con el inicio de sesión, registro de usuarios y obtención de tokens.
 */
val repositoryDatabaseModule = module {
    factoryOf(::TokenDatabaseRepositoryImpl).bind(TokenDatabaseRepository::class)
    factoryOf(::DataStoreRepositoryImpl).bind(DataStoreRepository::class)
}