package com.network.api.repository

import com.network.api.request.ApolloRequestQr
import com.network.api.response.ApiResponse

interface ApolloRepository {
    suspend fun login(apolloRequestQr: ApolloRequestQr): ApiResponse
}