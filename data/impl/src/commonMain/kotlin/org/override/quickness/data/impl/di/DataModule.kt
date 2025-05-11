package org.override.quickness.data.impl.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.override.quickness.data.impl.datastore.createDataStore
import org.override.quickness.data.impl.repository.DataStoreRepositoryImpl
import org.override.quickness.data.impl.repository.TokenDatabaseRepositoryImpl
import org.override.quickness.data.impl.room.CreateDatabase
import org.override.quickness.data.api.repository.DataStoreRepository
import org.override.quickness.data.api.repository.TokenDatabaseRepository
import org.override.quickness.data.api.room.dao.TokenDao
import org.override.quickness.data.impl.room.db.TokenDatabase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule: Module get() = module {
    singleOf(::createDataStore).bind<DataStore<Preferences>>()
    single<TokenDatabase> { CreateDatabase(get()).getTokenDatabase() }
    single<TokenDao> { get<TokenDatabase>().tokenDao() }
}

val repositoryDatabaseModule: Module get() = module {
    factoryOf(::TokenDatabaseRepositoryImpl).bind(TokenDatabaseRepository::class)
    factoryOf(::DataStoreRepositoryImpl).bind(DataStoreRepository::class)
}

expect val nativeDatabase: Module