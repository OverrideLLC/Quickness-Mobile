package org.quickness.interfaces.repository.data

import org.quickness.data.room.entity.TokenEntity

interface TokenDatabaseRepository {
    suspend fun getAllTokens(): List<TokenEntity>
    suspend fun saveTokens(tokens: Map<Int, String>, currentTime: Long)
    suspend fun clearTokens()
    suspend fun getTokenByIndex(index: Int): TokenEntity?
    suspend fun getTokenById(id: Int): TokenEntity?
}