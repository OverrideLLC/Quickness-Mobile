package org.quickness.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.quickness.utils.ContextProvider

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