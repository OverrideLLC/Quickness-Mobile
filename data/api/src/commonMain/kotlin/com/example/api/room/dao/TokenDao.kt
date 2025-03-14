package com.example.api.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.api.room.entity.TokenEntity

@Dao
interface TokenDao {
    @Query("SELECT * FROM tokens WHERE id = :id")
    suspend fun getTokenById(id: Int): TokenEntity?

    @Query("SELECT * FROM tokens")
    suspend fun getAllTokens(): List<TokenEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTokens(tokens: List<TokenEntity>)

    @Query("DELETE FROM tokens")
    suspend fun deleteAllTokens()

    @Query("SELECT * FROM tokens WHERE id = :index LIMIT 1")
    suspend fun getTokenByIndex(index: Int): TokenEntity?
}