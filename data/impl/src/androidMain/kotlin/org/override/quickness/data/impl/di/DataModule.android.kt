package org.override.quickness.data.impl.di

import androidx.room.RoomDatabase
import org.override.quickness.data.impl.room.androidDatabaseBuilder
import org.override.quickness.data.impl.room.db.TokenDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val nativeDatabase: Module
    get() = module {
        single<RoomDatabase.Builder<TokenDatabase>> { androidDatabaseBuilder() }
    }