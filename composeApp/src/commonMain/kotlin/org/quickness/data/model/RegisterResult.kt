package org.quickness.data.model

import kotlinx.serialization.Serializable

/**
 * Representa el resultado de un proceso de registro de usuario.
 *
 * @property status Indica el estado del registro, como "success" o "error".
 * @property uid Identificador único del usuario registrado. Se genera al completar el registro exitosamente.
 */
@Serializable
data class RegisterResult(
    val status: String, // Estado del proceso de registro (por ejemplo, éxito o error).
    val uid: String // UID generado para el usuario al registrarse exitosamente.
)
