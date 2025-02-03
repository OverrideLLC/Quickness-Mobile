package org.quickness.network.repository

import org.koin.core.logger.EmptyLogger
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.quickness.interfaces.repository.network.TokensRepository
import org.quickness.network.response.TokensResponse
import org.quickness.network.service.TokensService

/**
 * Implementation of the TokensRepository interface. This class is responsible for
 * managing and retrieving user tokens using the [TokensService].
 *
 * It provides a method to get the tokens associated with a specific user ID.
 */
class TokensRepositoryImpl(
    private val tokensService: TokensService
) : TokensRepository {
    /**
     * Obtiene los tokens de un usuario específico utilizando el servicio de tokens.
     *
     * @param JWT Identificador único del usuario.
     * @return [TokensResponse] que contiene el estado y los tokens obtenidos.
     */
    override suspend fun getTokens(JWT: String): TokensResponse {
        // Llamada al servicio de tokens para obtener los tokens del usuario
        return try {
            tokensService.getTokens(JWT).also {
                println(it.tokens)
            }
        } catch (e: Exception) {
            EmptyLogger().log(Level.DEBUG) { "Error al obtener tokens: ${e.message}" }
            TokensResponse(status = e.message ?: "Error connecting to server", tokens = emptyMap())
        }
    }
}
