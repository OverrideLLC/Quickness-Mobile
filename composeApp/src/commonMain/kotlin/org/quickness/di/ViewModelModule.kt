package org.quickness.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.quickness.ui.screens.home.HomeViewModel
import org.quickness.ui.screens.home.qr.QrViewModel
import org.quickness.ui.screens.home.settings.SettingsViewModel
import org.quickness.ui.screens.home.settings.screens.settings_qr.QrSettingsViewModel
import org.quickness.ui.screens.home.shop.ShopViewModel
import org.quickness.ui.screens.login.LoginViewModel
import org.quickness.ui.screens.register.RegisterViewModel

/**
 * Módulo de Koin que proporciona las dependencias de los ViewModels de la aplicación.
 *
 * Este módulo registra los ViewModels que se utilizan en la aplicación para las pantallas de inicio de sesión,
 * registro, configuración y visualización de códigos QR, así como la pantalla de inicio y configuración de QR.
 */

// Módulo que incluye los ViewModels utilizados en las pantallas de inicio y configuración.
val viewModelsHome = module {
    // Registro de QrViewModel para gestionar la lógica de la pantalla de visualización de código QR.
    viewModelOf(::QrViewModel)

    // Registro de SettingsViewModel para gestionar la lógica de la pantalla de configuración.
    viewModelOf(::SettingsViewModel)

    // Registro de QrSettingsViewModel para gestionar la lógica de la pantalla de configuración de QR.
    viewModelOf(::QrSettingsViewModel)
}

// Módulo que incluye los ViewModels utilizados en las pantallas de inicio de sesión y registro.
val viewModelsStart = module {
    // Registro de LoginViewModel para gestionar la lógica de la pantalla de inicio de sesión.
    viewModelOf(::LoginViewModel)

    // Registro de RegisterViewModel para gestionar la lógica de la pantalla de registro de usuario.
    viewModelOf(::RegisterViewModel)

    // Registro de HomeViewModel para gestionar la lógica de la pantalla principal de la aplicación.
    viewModelOf(::HomeViewModel)

    viewModelOf(::ShopViewModel)
}