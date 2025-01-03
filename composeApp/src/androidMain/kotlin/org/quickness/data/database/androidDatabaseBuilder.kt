package org.quickness.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import org.quickness.data.database.db.TokenDatabase
import org.quickness.di.ContextProvider
import org.quickness.utils.`object`.DatabaseObjects

fun androidDatabaseBuilder(): RoomDatabase.Builder<TokenDatabase> {
    val context = ContextProvider.getContext()!!
    val dbFile = context.getDatabasePath(DatabaseObjects.DATABASE_TOKEN_NAME)
    return Room.databaseBuilder<TokenDatabase>(
        context = context,
        name = dbFile.absolutePath,
    )
}