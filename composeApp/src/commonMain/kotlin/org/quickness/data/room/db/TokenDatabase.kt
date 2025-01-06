@file:Suppress("KotlinNoActualForExpect", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.quickness.data.room.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.quickness.data.room.dao.TokenDao
import org.quickness.data.room.entity.TokenEntity

@Database(
    entities = [TokenEntity::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(TokenDatabaseConstructor::class)
abstract class TokenDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}

expect object TokenDatabaseConstructor : RoomDatabaseConstructor<TokenDatabase> {
    override fun initialize(): TokenDatabase
}