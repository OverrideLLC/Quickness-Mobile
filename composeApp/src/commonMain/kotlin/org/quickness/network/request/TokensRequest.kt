package org.quickness.network.request

import kotlinx.serialization.Serializable

/**
 * Representa una solicitud para obtener tokens de un usuario.
 *
 * @property JWT Token de autenticación JWT.
 */
@Serializable
data class TokensRequest(
    val JWT: String,
    val root: String
)