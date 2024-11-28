package org.quickness.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.quickness.data.remote.RegisterService
import org.quickness.data.remote.TokensService

val networkModule = module {
    factoryOf(::RegisterService)
    factoryOf(::TokensService)
}
