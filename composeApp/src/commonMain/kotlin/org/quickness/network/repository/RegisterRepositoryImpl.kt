package org.quickness.network.repository

import org.quickness.interfaces.repository.network.RegisterRepository
import org.quickness.network.response.ApiResponse
import org.quickness.network.service.RegisterService

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