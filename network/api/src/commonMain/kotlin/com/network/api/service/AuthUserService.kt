package com.network.api.service

import com.network.api.request.AuthUserRequest
import com.network.api.response.ApiResponse

interface AuthUserService {
    suspend fun jwt(authRequest: AuthUserRequest): ApiResponse
}