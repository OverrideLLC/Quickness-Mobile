package org.quickness.data.request

import kotlinx.serialization.Serializable

/**
 * Representa una solicitud para obtener tokens de un usuario.
 *
 * @property uid Identificador único del usuario para el que se solicita el token.
 */
@Serializable
data class TokensRequest(
    val uid: String
)