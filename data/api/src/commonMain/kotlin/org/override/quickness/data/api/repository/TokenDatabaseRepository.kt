<<<<<<<< HEAD:data/api/src/commonMain/kotlin/org/override/quickness/data/api/repository/TokenDatabaseRepository.kt
package org.override.quickness.data.api.repository
========
package org.quickness.interfaces.repository
>>>>>>>> origin/master:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/interfaces/repository/TokenDatabaseRepository.kt

import org.override.quickness.data.api.room.entity.TokenEntity

interface TokenDatabaseRepository {
    suspend fun getAllTokens(): List<TokenEntity>
    suspend fun saveTokens(tokens: Map<Int, String>, currentTime: Long)
    suspend fun clearTokens()
    suspend fun getTokenByIndex(index: Int): TokenEntity?
    suspend fun getTokenById(id: Int): TokenEntity?
}