package org.quickness.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val NativeModule = module {
    single { Darwin.create() }
    single { "crateHttpClient(get())" }
}