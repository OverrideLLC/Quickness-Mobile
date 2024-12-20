package org.quickness.data.repository

import org.quickness.data.Result.TokensResult
import org.quickness.data.service.TokensService

class TokensRepository(private val tokensService: TokensService) {

    /**
     * Obtiene los tokens de un usuario específico utilizando el servicio de tokens.
     *
     * @param uid Identificador único del usuario.
     * @return [TokensResult] que contiene el estado y los tokens obtenidos.
     */
    suspend fun getTokens(uid: String): TokensResult {
        // Llamada al servicio de tokens para obtener los tokens del usuario
        return try {
            tokensService.getTokens(uid)
        } catch (e: Exception) {
            TokensResult(status = e.message ?: "Error connecting to server", tokens = emptyMap())
        }
    }
}
