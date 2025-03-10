package com.data.impl.room

import androidx.room.Room
import androidx.room.RoomDatabase
import com.data.impl.room.db.TokenDatabase
import com.quickness.shared.utils.objects.DatabaseObjects
import com.quickness.shared.utils.providers.ContextProvider

fun androidDatabaseBuilder(): RoomDatabase.Builder<TokenDatabase> {
    val context = ContextProvider.getContext()!!
    val dbFile = context.getDatabasePath(DatabaseObjects.DATABASE_TOKEN_NAME)
    return Room.databaseBuilder<TokenDatabase>(
        context = context,
        name = dbFile.absolutePath,
    )
}