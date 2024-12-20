package org.quickness.data.repository

import org.quickness.data.Result.RegisterResult
import org.quickness.data.service.RegisterService

class RegisterRepository(private val registerService: RegisterService) {

    /**
     * Realiza el registro de un nuevo usuario utilizando el servicio de registro.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param name Nombre del usuario.
     * @param curp CURP del usuario.
     * @param phoneNumber Número de teléfono del usuario.
     * @return [RegisterResult] que contiene el estado del registro y el UID del usuario.
     */
    suspend fun register(
        email: String,
        password: String,
        name: String,
        curp: String,
        phoneNumber: String
    ): RegisterResult {
        // Llamada al servicio de registro para crear un nuevo usuario
        return registerService.register(email, password, name, curp, phoneNumber)
    }
}