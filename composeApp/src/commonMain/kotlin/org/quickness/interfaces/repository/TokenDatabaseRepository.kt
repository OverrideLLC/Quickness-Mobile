package org.quickness.interfaces.repository

import org.quickness.data.room.entity.TokenEntity

interface TokenDatabaseRepository {
    suspend fun insertTokens(tokens: List<TokenEntity>)
}