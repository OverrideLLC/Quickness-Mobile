package com.data.impl.repository

import com.example.api.repository.TokenDatabaseRepository
import com.example.api.room.dao.TokenDao
import com.example.api.room.entity.TokenEntity

class TokenDatabaseRepositoryImpl(
    private val tokenDao: TokenDao
) : TokenDatabaseRepository {

    override suspend fun getAllTokens(): List<TokenEntity> {
        return tokenDao.getAllTokens()
    }

    override suspend fun saveTokens(tokens: Map<Int, String>, currentTime: Long) {
        val tokenEntities = tokens.map { TokenEntity(it.key, it.value, currentTime) }
        tokenDao.insertTokens(tokenEntities)
    }

    override suspend fun clearTokens() {
        tokenDao.deleteAllTokens()
    }

    override suspend fun getTokenByIndex(index: Int): TokenEntity? {
        val tokenEntity = tokenDao.getTokenByIndex(index)
        return tokenEntity
    }

    override suspend fun getTokenById(id: Int): TokenEntity? {
        val tokenEntity = tokenDao.getTokenById(id)
        return tokenEntity
    }
}