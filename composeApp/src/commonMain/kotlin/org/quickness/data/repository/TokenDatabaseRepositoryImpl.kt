package org.quickness.data.repository

import org.quickness.data.room.dao.TokenDao
import org.quickness.data.room.entity.TokenEntity
import org.quickness.interfaces.repository.TokenDatabaseRepository

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
}