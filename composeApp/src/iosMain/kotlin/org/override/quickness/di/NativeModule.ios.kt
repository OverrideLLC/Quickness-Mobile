package org.override.quickness.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val nativeModule = module {
    single { Darwin.create() }
}