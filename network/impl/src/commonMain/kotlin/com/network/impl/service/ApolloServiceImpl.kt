package com.network.impl.service

import com.network.api.repository.ApolloRepository
import com.network.api.request.ApolloRequestQr
import com.network.api.response.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject

class ApolloServiceImpl(
    private val httpClient: HttpClient
) : ApolloRepository {
    override suspend fun login(apolloRequestQr: ApolloRequestQr): ApiResponse {
        return try {
            httpClient.post {
                url(urlString = "https://user-mobile.quickness.cloud/qr")
                contentType(ContentType.Application.Json)
                setBody(apolloRequestQr)
            }.body()
        } catch (e: Exception) {
            println("Error al obtener tokens: ${e.message}")
            ApiResponse(
                status = 400,
                message = "Error al obtener tokens: ${e.message}",
                data = buildJsonObject { }
            )
        }
    }
}