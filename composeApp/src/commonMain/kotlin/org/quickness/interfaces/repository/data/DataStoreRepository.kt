package org.quickness.interfaces.repository.data

interface DataStoreRepository {
    suspend fun saveString(data: Map<String, String>)
    suspend fun getString(key: String, defaultValue: String): String?
    suspend fun saveInt(data: Map<String, Int>)
    suspend fun getInt(key: String, defaultValue: Int): Int?
    suspend fun saveLong(data: Map<String, Long>)
    suspend fun getLong(key: String, defaultValue: Long): Long?
    suspend fun saveBoolean(data: Map<String, Boolean>)
    suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean?
    suspend fun saveFloat(data: Map<String, Float>)
    suspend fun getFloat(key: String, defaultValue: Float): Float?
    suspend fun saveDouble(data: Map<String, Double>)
    suspend fun getDouble(key: String, defaultValue: Double): Double?
    suspend fun saveSet(key: String, value: Set<String>)
    suspend fun getSet(key: String, defaultValue: Set<String>): Set<String>?
}