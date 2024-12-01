package org.quickness.data.request

import kotlinx.serialization.Serializable

/**
 * Representa una solicitud de inicio de sesión que contiene el token de identificación del usuario.
 *
 * @property idtoken Token de identificación del usuario para autenticar la solicitud.
 */
@Serializable
data class LoginRequest(
    val idtoken: String
)
