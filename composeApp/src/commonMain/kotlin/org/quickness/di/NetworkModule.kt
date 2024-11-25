package org.quickness.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.quickness.data.remote.FirebaseService
import org.quickness.data.remote.RegisterService

val networkModule = module {
    factoryOf(::RegisterService)
}
