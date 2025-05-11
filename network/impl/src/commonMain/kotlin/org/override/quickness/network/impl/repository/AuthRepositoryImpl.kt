package org.override.quickness.network.impl.repository

import org.override.quickness.network.api.repository.AuthRepository
import org.override.quickness.network.api.request.AuthUserRequest
import org.override.quickness.network.api.response.ApiResponse
import org.override.quickness.network.api.response.AuthResponse
import org.override.quickness.network.api.service.AuthUserService
import org.override.quickness.network.api.service.FirebaseAuth
import kotlinx.io.IOException
import kotlinx.serialization.json.buildJsonObject

class AuthRepositoryImpl(
    private val firebaseService: FirebaseAuth,
    private val authService: AuthUserService
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResponse {
        return try {
            firebaseService.signIn(email, password)
        } catch (e: IOException) {
            AuthResponse(
                status = "500",
                message = e.message ?: "Error"
            ).also { println("API Response: ${it.message}") }
        } catch (e: Exception) {
            AuthResponse(
                status = "500",
                message = e.message ?: "Error"
            ).also { println("API Response: ${it.message}") }
        }
    }

    override suspend fun jwt(token: String): ApiResponse {
        return try {
            val request = AuthUserRequest(token)
            authService.jwt(request)
                .also { apiResponse -> println("API Response: ${apiResponse.data.values}") }
        } catch (e: IOException) {
            ApiResponse(
                message = "Error: ${e.message}",
                status = 500,
                data = buildJsonObject { }
            ).also { apiResponse -> println("API Response: ${apiResponse.message}") }
        } catch (_: Exception) {
            ApiResponse(
                message = "Error",
                status = 500,
                data = buildJsonObject { }
            ).also { apiResponse -> println("API Response: ${apiResponse.message}") }
        }
    }
}