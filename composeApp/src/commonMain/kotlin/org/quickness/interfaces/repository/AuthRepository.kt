package org.quickness.interfaces.repository

import org.quickness.data.Result.ApiResponse
import org.quickness.data.Result.AuthResult

/**
 * Repository interface for authentication-related operations.
 *
 * This interface defines the methods for user authentication and token management.
 * Implementations of this interface will provide the concrete logic for interacting
 * with the authentication system.
 */
interface AuthRepository {
    /**
     * Authenticates a user with the provided email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return An [AuthResult] object indicating the success or failure of the login attempt.
     *         This object will contain either the authenticated user or an error message.
     *
     * @throws [Exception] If an unexpected error occurs during the login process.
     *                     This could be due to network issues, server errors, etc.
     */
    suspend fun login(email: String, password: String): AuthResult
    /**
     * Verifies and decodes the provided JWT token.
     *
     * This function takes a JWT token as input, verifies its signature and expiry,
     * and then decodes the payload to extract information.
     *
     * @param token The JWT token to be verified and decoded.
     * @return An [ApiResponse] object containing the result of the operation.
     *         If the token is valid, the response will contain the decoded payload.
     *         If the token is invalid or expired, the response will contain an error message.
     */
    suspend fun jwt(token: String): ApiResponse
}