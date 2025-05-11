<<<<<<<< HEAD:network/impl/src/commonMain/kotlin/org/override/quickness/network/impl/repository/RegisterRepositoryImpl.kt
package org.override.quickness.network.impl.repository

import org.override.quickness.network.api.repository.RegisterRepository
import org.override.quickness.network.api.response.ApiResponse
import org.override.quickness.network.api.service.RegisterService
========
package org.quickness.data.repository

import org.quickness.data.response.ApiResponse
import org.quickness.data.service.RegisterService
import org.quickness.interfaces.repository.RegisterRepository
>>>>>>>> origin/master:data/impl/src/commonMain/kotlin/org/override/quickness/data/impl/repository/RegisterRepositoryImpl.kt

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