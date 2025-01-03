package org.quickness.data.database.localdb

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.quickness.data.database.db.TokenDatabase

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