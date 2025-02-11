package org.quickness.network.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import org.quickness.BuildConfig
import org.quickness.network.response.ApiResponse
import org.quickness.network.request.AuthUserRequest

class AuthUserService(private val client: HttpClient) {
    suspend fun jwt(authRequest: AuthUserRequest): ApiResponse {
        return try {
            client.post {
                url(BuildConfig.AUTH_API_LINK)
                contentType(ContentType.Application.Json)
                setBody(authRequest)
            }.body<ApiResponse>()
        } catch (e: Exception) {
            return ApiResponse(
                message = "Error: ${e.message}",
                status = 500,
                data = buildJsonObject { }
            )
        }
    }
}