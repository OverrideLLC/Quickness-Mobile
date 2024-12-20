package org.quickness.data.request

import kotlinx.serialization.Serializable

/**
 * Representa una solicitud de inicio de sesión que contiene el token de identificación del usuario.
 *
 * @property token Token de identificación del usuario para autenticar la solicitud.
 */
@Serializable
data class AuthUserRequest(
    val token: String
)
