package org.override.quickness.data.impl.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.override.quickness.shared.utils.ContextProvider

actual fun createDataStore(): DataStore<Preferences> {
    val context = ContextProvider.getContext()!!
    return AppSettings.createDataStore(
        producePath = {
            context
                .filesDir
                .resolve(dataStoreFileName)
                .absolutePath
        }
    )
}