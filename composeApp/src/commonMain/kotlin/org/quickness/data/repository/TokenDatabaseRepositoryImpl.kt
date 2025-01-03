package org.quickness.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.quickness.data.database.db.TokenDatabase
import org.quickness.data.database.entity.TokenEntity
import org.quickness.interfaces.repository.TokenDatabaseRepository

class TokenDatabaseRepositoryImpl(private val database: TokenDatabase) : TokenDatabaseRepository {
    private val dispatcher = Dispatchers.IO

    override suspend fun insertTokens(tokens: List<TokenEntity>) {
        with(dispatcher) {
            database.tokenDao().insertTokens(tokens)
        }
    }

}