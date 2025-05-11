package org.override.quickness.network.api.service

import org.override.quickness.network.api.request.AuthUserRequest
import org.override.quickness.network.api.response.ApiResponse

interface AuthUserService {
    suspend fun jwt(authRequest: AuthUserRequest): ApiResponse
}