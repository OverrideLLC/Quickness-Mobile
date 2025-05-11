package org.override.quickness.network.api.repository

import org.override.quickness.network.api.request.ApolloRequestQr
import org.override.quickness.network.api.response.ApiResponse

interface ApolloRepository {
    suspend fun login(apolloRequestQr: ApolloRequestQr): ApiResponse
}