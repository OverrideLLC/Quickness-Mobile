<<<<<<<< HEAD:network/impl/src/commonMain/kotlin/org/override/quickness/network/impl/service/AuthUserServiceImpl.kt
package org.override.quickness.network.impl.service
========
package org.quickness.data.service
>>>>>>>> origin/master:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/data/service/AuthUserService.kt

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
<<<<<<<< HEAD:network/impl/src/commonMain/kotlin/org/override/quickness/network/impl/service/AuthUserServiceImpl.kt
import org.override.quickness.network.api.request.AuthUserRequest
import org.override.quickness.network.api.response.ApiResponse
import org.override.quickness.network.api.service.AuthUserService
========
import org.quickness.data.response.ApiResponse
import org.quickness.data.request.AuthUserRequest
import org.quickness.utils.`object`.ApiLinks
>>>>>>>> origin/master:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/data/service/AuthUserService.kt

class AuthUserServiceImpl(private val client: HttpClient) : AuthUserService {
    override suspend fun jwt(authRequest: AuthUserRequest): ApiResponse {
        return try {
            client.post {
<<<<<<<< HEAD:network/impl/src/commonMain/kotlin/org/override/quickness/network/impl/service/AuthUserServiceImpl.kt
                url("https://user-mobile.quickness.cloud/auth")
========
                url(ApiLinks.AUTH_API_LINK)
>>>>>>>> origin/master:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/data/service/AuthUserService.kt
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