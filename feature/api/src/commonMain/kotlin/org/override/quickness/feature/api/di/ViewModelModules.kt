package org.override.quickness.feature.api.di

import org.override.quickness.feature.api.NavigationViewModel
import org.override.quickness.feature.home.qr.screens.QrViewModel
import org.override.quickness.feature.home.screen.HomeViewModel
import org.override.quickness.feature.home.service.screen.ServiceViewModel
import org.override.quickness.feature.home.service.lyra.LyraViewModel
import org.override.quickness.feature.home.shop.screen.ShopViewModel
import org.override.quickness.feature.start.screen.StartViewModel
import org.override.quickness.feature.home.cam.CameraViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.override.quickness.feature.home.service.eva.EvaViewModel
import org.override.quickness.feature.home.settings.screen.SettingsViewModel
import org.override.quickness.feature.home.settings.screens_settings.settings_account.AccountSettingsViewModel
import org.override.quickness.feature.home.settings.screens_settings.settings_display.DisplaySettingsViewModel
import org.override.quickness.feature.home.settings.screens_settings.settings_privacy.PrivacySettingsViewModel
import org.override.quickness.feature.home.settings.screens_settings.settings_qr.QrSettingsViewModel

val viewModelModulesStart: Module
    get() = module {
        viewModelOf(::StartViewModel)
        //viewModelOf(::LoginViewModel) DEPRECATED
        viewModelOf(::HomeViewModel)
        viewModelOf(::NavigationViewModel)
        //viewModelOf(::RegisterViewModel) DEPRECATED
        viewModelOf(::CameraViewModel)
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
        viewModelOf(::DisplaySettingsViewModel)
    }

val viewModelModulesService: Module
    get() = module {
        viewModelOf(::LyraViewModel)
        viewModelOf(::EvaViewModel)
    }