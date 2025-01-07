package org.quickness.data.response

import kotlinx.serialization.Serializable

/**
 * Representa el resultado de un proceso relacionado con la obtención o manejo de tokens.
 *
 * @property status Indica el estado del proceso, como "success" o "error".
 * @property tokens Mapa de claves y valores que contiene los tokens generados o actualizados.
 *                  Las claves pueden representar el tipo de token (por ejemplo, "accessToken", "refreshToken"),
 *                  y los valores son los tokens en sí.
 */
@Serializable
data class TokensResponse(
    val status: String, // Estado del proceso (por ejemplo, éxito o error).
    val tokens: Map<String, String>, // Mapa con los tokens obtenidos o actualizados.
)
