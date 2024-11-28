package org.quickness.data.repository

import org.quickness.data.model.TokensResult
import org.quickness.data.remote.TokensService

class TokensRepository(private val tokensService: TokensService) {
    suspend fun getTokens(uid: String): TokensResult {
        return tokensService.getTokens(uid)
    }
}