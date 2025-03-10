package com.data.impl.di

import androidx.room.RoomDatabase
import com.data.impl.room.androidDatabaseBuilder
import com.example.api.room.db.TokenDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val nativeDatabase: Module
    get() = module {
        single<RoomDatabase.Builder<TokenDatabase>> { androidDatabaseBuilder() }
    }