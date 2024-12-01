package org.quickness.di

import android.content.Context
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual val NativeModule = module {
    single { OkHttp.create() }
    single { crateHttpClient(get()) }
}

object ContextProvider {
    private var applicationContext: Context? = null
    fun initialize(context: Context) {
        applicationContext = context
    }

    fun getContext(): Context? {
        return applicationContext
    }
}