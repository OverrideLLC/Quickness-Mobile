package com.network.api.service

import com.network.api.response.ApiResponse

interface RegisterService {
    suspend fun register(
        email: String,
        password: String,
        name: String,
        curp: String,
        phoneNumber: String,
    ): ApiResponse
}