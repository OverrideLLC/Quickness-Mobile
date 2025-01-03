package org.quickness.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import org.quickness.data.database.entity.TokenEntity

@Dao
interface TokenDao {
    @Insert
    suspend fun insertTokens(tokens: List<TokenEntity>)
}