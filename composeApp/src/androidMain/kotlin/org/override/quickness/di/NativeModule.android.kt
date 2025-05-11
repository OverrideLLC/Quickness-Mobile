package org.override.quickness.di

import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual val nativeModule = module {
    single { OkHttp.create() }
}