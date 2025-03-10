package org.quickness.di

import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val NativeModule = module {
    single { OkHttp.create() }
    singleOf(::createHttpClient)
}