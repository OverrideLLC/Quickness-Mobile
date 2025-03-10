package com.data.impl.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.data.impl.datastore.createDataStore
import com.data.impl.repository.DataStoreRepositoryImpl
import com.data.impl.repository.TokenDatabaseRepositoryImpl
import com.data.impl.room.CreateDatabase
import com.example.api.repository.DataStoreRepository
import com.example.api.repository.TokenDatabaseRepository
import com.example.api.room.dao.TokenDao
import com.example.api.room.db.TokenDatabase
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