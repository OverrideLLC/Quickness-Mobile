package com.network.impl.repository

import com.network.api.repository.RegisterRepository
import com.network.api.response.ApiResponse
import com.network.api.service.RegisterService

/**
 * Implementation of the `RegisterRepository` interface.
 *
 * This class handles the registration of new users by delegating the process
 * to the `RegisterService`.
 *
 * @property registerService The service responsible for performing the registration logic.
 */
class RegisterRepositoryImpl(
    private val registerService: RegisterService
) : RegisterRepository {

    /**
     * Registers a new user.
     *
     * This function delegates the registration process to the `registerService`.
     *
     * @param email The email address of the user.
     * @param password The password for the user's account.
     * @param name The full name of the user.
     * @param curp The CURP (Clave Única de Registro de Población) of the user.
     * @param phoneNumber The phone number of the user.
     * @return An `ApiResponse` object indicating the success or failure of the registration.
     */
    override suspend fun register(
        email: String,
        password: String,
        name: String,
        curp: String,
        phoneNumber: String
    ): ApiResponse {
        return registerService.register(email, password, name, curp, phoneNumber)
    }
}