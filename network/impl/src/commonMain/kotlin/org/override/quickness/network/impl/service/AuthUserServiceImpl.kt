package org.override.quickness.network.impl.service
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import org.override.quickness.network.api.request.AuthUserRequest
import org.override.quickness.network.api.response.ApiResponse
import org.override.quickness.network.api.service.AuthUserService

class AuthUserServiceImpl(private val client: HttpClient) : AuthUserService {
    override suspend fun jwt(authRequest: AuthUserRequest): ApiResponse {
        return try {
            client.post {
                url("https://user-mobile.quickness.cloud/auth")
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