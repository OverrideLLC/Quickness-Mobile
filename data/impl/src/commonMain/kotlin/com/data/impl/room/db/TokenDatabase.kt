@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.data.impl.room.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.example.api.room.dao.TokenDao
import com.example.api.room.entity.TokenEntity

@Database(
    entities = [TokenEntity::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(TokenDatabaseConstructor::class)
abstract class TokenDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}

@Suppress("KotlinNoActualForExpect")
internal expect object TokenDatabaseConstructor : RoomDatabaseConstructor<TokenDatabase> {
    override fun initialize(): TokenDatabase
}