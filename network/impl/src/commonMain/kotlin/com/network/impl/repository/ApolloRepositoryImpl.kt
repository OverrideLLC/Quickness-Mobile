package com.network.impl.repository

import com.network.api.repository.ApolloRepository
import com.network.api.request.ApolloRequestQr
import com.network.api.response.ApiResponse
import com.network.api.service.ApolloService
import kotlinx.serialization.json.buildJsonObject

class ApolloRepositoryImpl(private val apolloService: ApolloService) : ApolloRepository {
    override suspend fun login(apolloRequestQr: ApolloRequestQr): ApiResponse {
        return try {
            apolloService.login(apolloRequestQr)
        } catch (e: Exception) {
            ApiResponse(
                status = 500,
                message = e.message.toString(),
                data = buildJsonObject { }
            )
        }
    }
}