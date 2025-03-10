package com.feature.di

import com.feature.login.screen.LoginViewModel
import com.feature.start.screen.StartViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModules: Module
    get() = module {
        viewModelOf(::StartViewModel)
        viewModelOf(::LoginViewModel)
    }