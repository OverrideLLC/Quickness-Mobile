package org.override.quickness.data.impl.room

import androidx.room.Room
import androidx.room.RoomDatabase
import org.override.quickness.data.impl.room.db.TokenDatabase
import org.override.quickness.shared.utils.objects.DatabaseObjects
import org.override.quickness.shared.utils.ContextProvider

fun androidDatabaseBuilder(): RoomDatabase.Builder<TokenDatabase> {
    val context = ContextProvider.getContext()!!
    val dbFile = context.getDatabasePath(DatabaseObjects.DATABASE_TOKEN_NAME)
    return Room.databaseBuilder<TokenDatabase>(
        context = context,
        name = dbFile.absolutePath,
    )
}