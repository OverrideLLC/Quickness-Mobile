package org.override.quickness.network.api.service

import org.override.quickness.network.api.request.ApolloRequestQr
import org.override.quickness.network.api.response.ApiResponse

interface ApolloService {
    suspend fun login(apolloRequestQr: ApolloRequestQr): ApiResponse
}