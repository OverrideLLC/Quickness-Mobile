package org.quickness.data.room

import androidx.room.Room
import androidx.room.RoomDatabase
import com.quickness.shared.utils.objects.DatabaseObjects
import org.quickness.data.room.db.TokenDatabase
import org.quickness.utils.ContextProvider

fun androidDatabaseBuilder(): RoomDatabase.Builder<TokenDatabase> {
    val context = ContextProvider.getContext()!!
    val dbFile = context.getDatabasePath(DatabaseObjects.DATABASE_TOKEN_NAME)
    return Room.databaseBuilder<TokenDatabase>(
        context = context,
        name = dbFile.absolutePath,
    )
}