package org.quickness.data.repository

import org.quickness.data.Result.ApiResponse
import org.quickness.data.Result.AuthResult
import org.quickness.data.request.AuthUserRequest
import org.quickness.data.service.AuthUserService
import org.quickness.data.service.FirebaseAuthImpl
import org.quickness.interfaces.repository.AuthRepository

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
     * @return An [AuthResult] object indicating the success or failure of the login attempt.
     *         This object may contain the user's UID and other authentication details on success.
     */
    override suspend fun login(email: String, password: String): AuthResult {
        return firebaseService.signIn(email, password)
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
        return authService.jwt(AuthUserRequest(token))
    }
}
