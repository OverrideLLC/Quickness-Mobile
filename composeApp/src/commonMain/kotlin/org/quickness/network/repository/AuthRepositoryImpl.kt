package org.quickness.network.repository

import kotlinx.serialization.json.buildJsonObject
import org.quickness.interfaces.repository.network.AuthRepository
import org.quickness.network.request.AuthUserRequest
import org.quickness.network.response.ApiResponse
import org.quickness.network.response.AuthResponse
import org.quickness.network.service.AuthUserService
import org.quickness.network.service.FirebaseAuthImpl

/**
 * Implementation of the `AuthRepository` interface.
 *
 * This class handles user authentication and authorization using Firebase and a separate authentication service.
 * It provides methods for logging in users and verifying JWT tokens.
 *
 * @property firebaseService An instance of `FirebaseService` used for Firebase-based authentication.
 * @property authService An instance of `AuthUserService` used for JWT verification and user information retrieval.
 */
class AuthRepositoryImpl(
    private val firebaseService: FirebaseAuthImpl,
    private val authService: AuthUserService
) : AuthRepository {

    /**
     * Logs in a user with the provided email and password.
     *
     * This function delegates the authentication process to the `firebaseService`.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return An [AuthResponse] object indicating the success or failure of the login attempt.
     *         This object may contain the user's UID and other authentication details on success.
     */
    override suspend fun login(email: String, password: String): AuthResponse {
        return try {
            firebaseService.signIn(email, password)
        } catch (e: Exception) {
            AuthResponse(
                status = "500",
                message = e.message ?: "Error"
            ).also {
                println("API Response: ${it.message}")
            }
        }
    }

    /**
     * Verifies the given JWT token and returns an API response.
     *
     * This function delegates the JWT verification to the `authService` using the provided token.
     *
     * @param token The JWT token to verify.
     * @return An ApiResponse indicating the result of the verification.
     *         It may contain user information if the token is valid, or an error message if it's invalid.
     */
    override suspend fun jwt(token: String): ApiResponse {
        return try {
            authService.jwt(AuthUserRequest(token)).also { apiResponse ->
                println("API Response: ${apiResponse.data.values}")
            }
        } catch (e: Exception) {
            ApiResponse(
                message = "Error: ${e.message}",
                status = 500,
                data = buildJsonObject { }
            ).also { apiResponse ->
                println("API Response: ${apiResponse.message}")
            }
        }
    }
}
