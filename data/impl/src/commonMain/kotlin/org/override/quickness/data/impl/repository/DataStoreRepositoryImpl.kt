package org.override.quickness.data.impl.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import org.override.quickness.data.api.repository.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    private val dispatcher = Dispatchers.IO
    override suspend fun saveString(data: Map<String, String>) {
        withContext(dispatcher) {
            dataStore.edit { dataStore ->
                data.forEach {
                    dataStore[stringPreferencesKey(it.key)] = it.value
                }
            }
        }
    }

    override suspend fun getString(
        key: String,
        defaultValue: String
    ): String? {
        return withContext(dispatcher) {
            dataStore.data.map {
                it[stringPreferencesKey(key)]
            }.first() ?: defaultValue.also { value ->
                saveString(mapOf(key to value))
            }
        }
    }

    override suspend fun saveInt(data: Map<String, Int>) {
        withContext(dispatcher) {
            dataStore.edit { dataStore ->
                data.forEach {
                    dataStore[intPreferencesKey(it.key)] = it.value
                }
            }
        }
    }

    override suspend fun getInt(key: String, defaultValue: Int): Int? {
        return withContext(dispatcher) {
            dataStore.data.map {
                it[intPreferencesKey(key)]
            }.first() ?: defaultValue.also { value ->
                saveInt(mapOf(key to value))
            }
        }
    }

    override suspend fun saveLong(data: Map<String, Long>) {
        withContext(dispatcher) {
            dataStore.edit { dataStore ->
                data.forEach {
                    dataStore[longPreferencesKey(it.key)] = it.value
                }
            }
        }
    }

    override suspend fun getLong(key: String, defaultValue: Long): Long? {
        return withContext(dispatcher) {
            dataStore.data.map {
                it[longPreferencesKey(key)]
            }.first() ?: defaultValue.also { value ->
                saveLong(mapOf(key to value))
            }
        }
    }

    override suspend fun saveBoolean(data: Map<String, Boolean>) {
        withContext(dispatcher) {
            dataStore.edit { dataStore ->
                data.forEach {
                    dataStore[booleanPreferencesKey(it.key)] = it.value
                }
            }
        }
    }

    override suspend fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Flow<Boolean>? {
        return dataStore.data
            .catch {
                if (it is Exception) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map { preference ->
                preference[booleanPreferencesKey(key)] ?: defaultValue
            }
    }

    override suspend fun saveFloat(data: Map<String, Float>) {
        withContext(dispatcher) {
            dataStore.edit { dataStore ->
                data.forEach {
                    dataStore[floatPreferencesKey(it.key)] = it.value
                }
            }
        }
    }

    override suspend fun getFloat(key: String, defaultValue: Float): Float? {
        return withContext(dispatcher) {
            dataStore.data.map {
                it[floatPreferencesKey(key)]
            }.first() ?: defaultValue.also { value ->
                saveFloat(mapOf(key to defaultValue))
            }
        }
    }

    override suspend fun saveDouble(data: Map<String, Double>) {
        withContext(dispatcher) {
            dataStore.edit { dataStore ->
                data.forEach {
                    dataStore[floatPreferencesKey(it.key)] = it.value.toFloat()
                }
            }
        }
    }

    override suspend fun getDouble(
        key: String,
        defaultValue: Double
    ): Double? {
        return withContext(dispatcher) {
            dataStore.data.map {
                it[doublePreferencesKey(key)]
            }.first() ?: defaultValue.also { value ->
                saveDouble(mapOf(key to value))
            }
        }
    }

    override suspend fun saveSet(key: String, value: Set<String>) {
        withContext(dispatcher) {
            dataStore.edit { dataStore ->
                dataStore[stringSetPreferencesKey(key)] = value
            }
        }
    }

    override suspend fun getSet(
        key: String,
        defaultValue: Set<String>
    ): Set<String>? {
        return withContext(dispatcher) {
            dataStore.data.map {
                it[stringSetPreferencesKey(key)]
            }.first() ?: defaultValue.also { value ->
                saveSet(key, value)
            }
        }
    }

    override suspend fun clear() {
        withContext(dispatcher) {
            dataStore.edit {
                it.clear()
            }
        }
    }
}