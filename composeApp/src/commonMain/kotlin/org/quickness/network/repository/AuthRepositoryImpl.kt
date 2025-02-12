package org.quickness.network.repository

import kotlinx.serialization.json.buildJsonObject
import okio.IOException
import org.quickness.interfaces.repository.network.AuthRepository
import org.quickness.network.request.AuthUserRequest
import org.quickness.network.response.ApiResponse
import org.quickness.network.response.AuthResponse
import org.quickness.network.service.AuthUserService
import org.quickness.network.service.FirebaseAuthImpl

class AuthRepositoryImpl(
    private val firebaseService: FirebaseAuthImpl,
    private val authService: AuthUserService
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResponse {
        return try {
            firebaseService.signIn(email, password)
        } catch (e: IOException) {
            AuthResponse(
                status = "500",
                message = e.message ?: "Error"
            ).also {
                println("API Response: ${it.message}")
            }
        } catch (e: Exception) {
            AuthResponse(
                status = "500",
                message = e.message ?: "Error"
            ).also {
                println("API Response: ${it.message}")
            }
        }
    }

    override suspend fun jwt(token: String): ApiResponse {
        return try {
            authService.jwt(AuthUserRequest(token)).also { apiResponse ->
                println("API Response: ${apiResponse.data.values}")
            }
        } catch (e: IOException) {
            ApiResponse(
                message = "Error: ${e.message}",
                status = 500,
                data = buildJsonObject { }
            ).also { apiResponse ->
                println("API Response: ${apiResponse.message}")
            }
        } catch (_: Exception) {
            ApiResponse(
                message = "Error",
                status = 500,
                data = buildJsonObject { }
            ).also { apiResponse ->
                println("API Response: ${apiResponse.message}")
            }
        }
    }
}
