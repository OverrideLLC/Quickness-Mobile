package org.quickness.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.quickness.data.request.TokensRequest
import org.quickness.data.response.TokensResponse
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
            httpClient.post {
                url(ApiLinks.TOKENS_API_LINK)
                contentType(ContentType.Application.Json)
                setBody(
                    TokensRequest(
                        JWT = JWT,
                        root = "qr_tokens_generate"
                    )
                )
            }.body()
        } catch (e: Exception) {
            TokensResponse(status = e.message ?: "Error connecting to server", tokens = emptyMap())
        }
    }
}