package org.override.quickness.network.api.repository

import org.override.quickness.network.api.response.ApiResponse

/**
 * Repository interface for user registration operations.
 *
 * This interface defines the contract for interacting with the data layer
 * to register new users. Implementations of this interface will handle
 * the actual communication with the backend systems to create user accounts.
 */
interface RegisterRepository {
    /**
     * Registers a new user.
     *
     * This function attempts to register a new user with the provided information.
     * It will communicate with the backend to create the user account.
     *
     * @param email The email address of the user.
     * @param password The password for the user's account.
     * @param name The full name of the user.
     * @param curp The CURP (Clave Única de Registro de Población) of the user. This might be specific to certain regions.
     * @param phoneNumber The phone number of the user.
     *
     * @return An ApiResponse object indicating the success or failure of the registration process.
     *         This response might contain error messages or a success status.
     *
     * @throws [Exception] If an unexpected error occurs during the registration process,
     *         such as network issues or server errors. This could also be a custom exception type.
     */
    suspend fun register(
        email: String,
        password: String,
        name: String,
        curp: String,
        phoneNumber: String
    ): ApiResponse
}