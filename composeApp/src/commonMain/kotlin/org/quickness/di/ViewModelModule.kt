package org.quickness.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.quickness.ui.screens.home.qr.QrViewModel
import org.quickness.ui.screens.login.LoginViewModel
import org.quickness.ui.screens.register.RegisterViewModel

val viewModelsModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::QrViewModel)
}