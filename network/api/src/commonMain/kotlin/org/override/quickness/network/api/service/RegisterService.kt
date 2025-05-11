package org.override.quickness.network.api.service

import org.override.quickness.network.api.response.ApiResponse

interface RegisterService {
    suspend fun register(
        email: String,
        password: String,
        name: String,
        curp: String,
        phoneNumber: String,
    ): ApiResponse
}