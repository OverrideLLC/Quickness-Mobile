package org.override.quickness.network.impl.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.override.quickness.network.api.request.TokensRequest
import org.override.quickness.network.api.response.TokensResponse
import org.override.quickness.network.api.service.TokensService

class TokensServiceImpl(private val httpClient: HttpClient) : TokensService {

    /**
     * Solicita los tokens para un usuario específico desde el backend.
     *
     * @param uid Identificador único del usuario.
     * @return [com.network.api.response.TokensResponse] que contiene el estado y el mapa de tokens obtenidos.
     */
    override suspend fun getTokens(uid: String): TokensResponse {
        return try {
            // Construcción y envío de la solicitud HTTP
            val request = TokensRequest(
                uid = uid,
                root = "qr_tokens_generate"
            )

            httpClient.post {
                url(urlString = "https://tokens.quickness.cloud")
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        } catch (e: Exception) {
            // Manejo de errores
            Logger.DEFAULT.log("Error al obtener tokens: ${e.message}")
            TokensResponse(status = e.message ?: "Error connecting to server", tokens = emptyMap())
        }
    }
}