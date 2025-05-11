package org.override.quickness.network.impl.repository

import org.override.quickness.network.api.repository.ApolloRepository
import org.override.quickness.network.api.request.ApolloRequestQr
import org.override.quickness.network.api.response.ApiResponse
import org.override.quickness.network.api.service.ApolloService
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