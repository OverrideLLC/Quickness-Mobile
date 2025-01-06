package org.quickness.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import org.quickness.data.room.entity.TokenEntity

@Dao
interface TokenDao {
    @Insert
    suspend fun insertTokens(tokens: List<TokenEntity>)
}