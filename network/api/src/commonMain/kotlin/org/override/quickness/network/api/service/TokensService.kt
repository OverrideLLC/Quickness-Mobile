package org.override.quickness.network.api.service

import org.override.quickness.network.api.response.TokensResponse

interface TokensService {
    suspend fun getTokens(uid: String): TokensResponse
}