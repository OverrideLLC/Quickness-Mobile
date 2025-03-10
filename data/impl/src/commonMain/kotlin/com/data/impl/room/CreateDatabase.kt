package com.data.impl.room

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.api.room.db.TokenDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class CreateDatabase(
    private val builder: RoomDatabase.Builder<TokenDatabase>
) {
    fun getTokenDatabase(): TokenDatabase {
        return builder
            .fallbackToDestructiveMigration(dropAllTables = true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}