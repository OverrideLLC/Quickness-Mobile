package com.data.impl.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "dice.preferences_pb"

object AppSettings {
    fun createDataStore(producePath: () -> String): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath(
                produceFile = { producePath().toPath() }
            )
    }
}