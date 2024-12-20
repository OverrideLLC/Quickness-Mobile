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
import org.quickness.data.Result.TokensResult
import org.quickness.data.request.TokensRequest
import org.quickness.utils.`object`.Constants

class TokensService(private val httpClient: HttpClient) {

    /**
     * Solicita los tokens para un usuario específico desde el backend.
     *
     * @param uid Identificador único del usuario.
     * @return [TokensResult] que contiene el estado y el mapa de tokens obtenidos.
     */
    suspend fun getTokens(uid: String): TokensResult {
        return try {
            // Construcción y envío de la solicitud HTTP
            val request = TokensRequest(uid = uid)

            httpClient.post {
                url("${Constants.URL_BACK_END}/tokens")
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        } catch (e: Exception) {
            // Manejo de errores
            Logger.DEFAULT.log("Error al obtener tokens: ${e.message}")
            TokensResult(status = e.message ?: "Error connecting to server", tokens = emptyMap())
        }
    }
}