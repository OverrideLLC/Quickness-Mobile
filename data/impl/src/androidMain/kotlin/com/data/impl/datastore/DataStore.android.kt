package com.data.impl.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.quickness.shared.utils.providers.ContextProvider

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