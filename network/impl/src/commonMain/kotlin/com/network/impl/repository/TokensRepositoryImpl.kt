package com.network.impl.repository

import com.network.api.repository.TokensRepository
import com.network.api.response.TokensResponse
import com.network.api.service.TokensService

/**
 * Implementation of the TokensRepository interface. This class is responsible for
 * managing and retrieving user tokens using the [com.network.service.TokensServiceImpl].
 *
 * It provides a method to get the tokens associated with a specific user ID.
 */
class TokensRepositoryImpl(
    private val tokensService: TokensService
) : TokensRepository {
    /**
     * Obtiene los tokens de un usuario específico utilizando el servicio de tokens.
     *
     * @param uid Identificador único del usuario.
     * @return [com.network.api.response.TokensResponse] que contiene el estado y los tokens obtenidos.
     */
    override suspend fun getTokens(uid: String): TokensResponse {
        // Llamada al servicio de tokens para obtener los tokens del usuario
        return try {
            tokensService.getTokens(uid).also {
                println(it.tokens)
            }
        } catch (e: Exception) {
            println("Error al obtener tokens: ${e.message}")
            TokensResponse(status = e.message ?: "Error connecting to server", tokens = emptyMap())
        }
    }
}
