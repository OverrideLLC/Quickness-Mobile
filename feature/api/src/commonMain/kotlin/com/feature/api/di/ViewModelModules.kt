package com.feature.api.di

import com.feature.api.NavigationViewModel
import com.feature.home.qr.screens.QrViewModel
import com.feature.home.screen.HomeViewModel
import com.feature.home.service.screen.ServiceViewModel
import com.feature.home.settings.screen.SettingsViewModel
import com.feature.home.settings.screens_settings.settings_account.AccountSettingsViewModel
import com.feature.home.settings.screens_settings.settings_privacy.PrivacySettingsViewModel
import com.feature.home.settings.screens_settings.settings_qr.QrSettingsViewModel
import com.feature.home.shop.screen.ShopViewModel
import com.feature.login.screen.LoginViewModel
import com.feature.start.screen.StartViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModulesStart: Module
    get() = module {
        viewModelOf(::StartViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::NavigationViewModel)
    }

val viewModelModulesHome: Module
    get() = module {
        viewModelOf(::QrViewModel)
        viewModelOf(::ServiceViewModel)
        viewModelOf(::SettingsViewModel)
        viewModelOf(::ShopViewModel)
    }

val viewModelModulesSettings: Module
    get() = module {
        viewModelOf(::AccountSettingsViewModel)
        viewModelOf(::PrivacySettingsViewModel)
        viewModelOf(::QrSettingsViewModel)
    }