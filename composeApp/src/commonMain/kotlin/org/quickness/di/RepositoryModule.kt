package org.quickness.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.quickness.data.repository.AuthRepository
import org.quickness.data.repository.RegisterRepository

val repositoryModule = module {
    factoryOf(::AuthRepository)
    factoryOf(::RegisterRepository)
}