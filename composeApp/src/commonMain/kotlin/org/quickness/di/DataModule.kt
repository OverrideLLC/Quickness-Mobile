package org.quickness.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.dsl.module
import org.quickness.data.datastore.createDataStore

val dataModule = module {
    single<DataStore<Preferences>> { createDataStore() }
}
