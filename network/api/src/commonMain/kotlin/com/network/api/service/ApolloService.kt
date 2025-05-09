package com.network.api.service

import com.network.api.request.ApolloRequestQr
import com.network.api.response.ApiResponse

interface ApolloService {
    suspend fun login(apolloRequestQr: ApolloRequestQr): ApiResponse
}