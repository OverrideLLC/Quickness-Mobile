package com.network.api.request

import kotlinx.serialization.Serializable

/**
 * Representa una solicitud de registro de un nuevo usuario.
 *
 * @property email Correo electrónico del usuario.
 * @property password Contraseña del usuario para la cuenta.
 * @property name Nombre completo del usuario.
 * @property phone_number Número de teléfono del usuario.
 * @property curp CURP (Clave Única de Registro de Población) del usuario.
 */
@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val phone: String,
    val curp: String
)