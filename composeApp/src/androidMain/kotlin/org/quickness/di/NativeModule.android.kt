package org.quickness.di

import androidx.room.RoomDatabase
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.quickness.data.database.androidDatabaseBuilder
import org.quickness.data.database.db.TokenDatabase

actual val NativeModule = module {
    single { OkHttp.create() }
    singleOf(::createHttpClient)
    single<RoomDatabase.Builder<TokenDatabase>> { androidDatabaseBuilder() }
}