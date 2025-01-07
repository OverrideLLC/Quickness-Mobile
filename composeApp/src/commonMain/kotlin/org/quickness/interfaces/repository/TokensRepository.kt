package org.quickness.interfaces.repository

import org.quickness.data.response.TokensResponse

/**
 * Repository interface responsible for managing and accessing user authentication tokens.
 *
 * This interface defines the methods for retrieving and potentially storing user tokens,
 * such as access tokens and refresh tokens. Implementations of this interface
 * will provide concrete mechanisms for interacting with a token storage system,
 * which could be a database, secure storage, or any other suitable storage solution.
 *
 * The primary purpose of this repository is to abstract the token management logic
 * from other parts of the application, promoting code modularity and maintainability.
 * By using this interface, the application can access and manipulate user tokens
 * without being tightly coupled to a specific storage implementation.
 */
interface TokensRepository {
    /**
     * Retrieves the authentication tokens for a user identified by their UID.
     *
     * This function fetches the access token and refresh token associated with the given user ID.
     * It is typically used to authenticate a user and provide access to protected resources.
     *
     * @param JWT The unique identifier of the user.
     * @return A [TokensResponse] object containing the access token and refresh token.
     *         If an error occurs during token retrieval, the result will contain an error message.
     *
     * @throws [Exception] if there is an issue retrieving the tokens. This could be due to
     *                     network connectivity problems, invalid credentials, or server errors.
     */
    suspend fun getTokens(JWT: String): TokensResponse
}