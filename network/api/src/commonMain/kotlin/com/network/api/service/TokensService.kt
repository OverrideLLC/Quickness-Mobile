package com.network.api.service

import com.network.api.response.TokensResponse

interface TokensService {
    suspend fun getTokens(JWT: String): TokensResponse
}