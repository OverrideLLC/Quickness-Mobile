package org.quickness.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.quickness.data.response.TokensResponse
import org.quickness.data.request.TokensRequest
import org.quickness.utils.`object`.ApiLinks

class TokensService(private val httpClient: HttpClient) {

    /**
     * Solicita los tokens para un usuario específico desde el backend.
     *
     * @param JWT Identificador único del usuario.
     * @return [TokensResponse] que contiene el estado y el mapa de tokens obtenidos.
     */
    suspend fun getTokens(JWT: String): TokensResponse {
        return try {
            // Construcción y envío de la solicitud HTTP
            val request = TokensRequest(
                JWT = JWT,
                root = "qr_tokens_generate"
            )

            httpClient.post {
                url(urlString = ApiLinks.TOKENS_API_LINK)
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