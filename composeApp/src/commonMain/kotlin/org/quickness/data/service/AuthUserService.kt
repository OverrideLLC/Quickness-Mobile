package org.quickness.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import org.quickness.data.Result.ApiResponse
import org.quickness.data.request.AuthUserRequest
import org.quickness.utils.`object`.ApiLinks

class AuthUserService(private val client: HttpClient) {
    suspend fun jwt(authRequest: AuthUserRequest): ApiResponse {
        return try {
            client.post {
                url(ApiLinks.AUTH_API_LINK)
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