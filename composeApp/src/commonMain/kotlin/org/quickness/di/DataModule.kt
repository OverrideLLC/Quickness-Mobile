package org.quickness.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.quickness.SharedPreference
import org.quickness.data.datastore.createDataStore

/**
 * Módulo de Koin que proporciona las dependencias relacionadas con los datos de la aplicación.
 *
 * Este módulo registra las instancias de:
 * - [SharedPreference] para la gestión de preferencias compartidas.
 */
val dataModule = module {
    singleOf(::SharedPreference)
    single<DataStore<Preferences>> { createDataStore() }
}
