package org.quickness.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.quickness.data.repository.LoginRepository
import org.quickness.data.repository.RegisterRepository
import org.quickness.data.repository.TokensRepository

val repositoryModule = module {
    factoryOf(::LoginRepository)
    factoryOf(::RegisterRepository)
    factoryOf(::TokensRepository)
}