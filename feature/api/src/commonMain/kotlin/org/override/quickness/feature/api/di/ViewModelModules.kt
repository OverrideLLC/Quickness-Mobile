package org.override.quickness.feature.api.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.override.quickness.feature.api.NavigationViewModel
import org.override.quickness.feature.eva.screen.EvaViewModel
import org.override.quickness.feature.home.cam.analyzer.CamAnalyzerViewModel
import org.override.quickness.feature.home.cam.scanner.CameraViewModel
import org.override.quickness.feature.home.qr.screens.QrViewModel
import org.override.quickness.feature.home.screen.HomeViewModel
import org.override.quickness.feature.home.service.lyra.LyraViewModel
import org.override.quickness.feature.home.service.screen.WidgetsViewModel
import org.override.quickness.feature.home.settings.screen.SettingsViewModel
import org.override.quickness.feature.home.settings.screens_settings.settings_account.SettingsAccountViewModel
import org.override.quickness.feature.home.settings.screens_settings.settings_display.DisplaySettingsViewModel
import org.override.quickness.feature.home.settings.screens_settings.settings_privacy.PrivacySettingsViewModel
import org.override.quickness.feature.home.settings.screens_settings.settings_qr.QrSettingsViewModel
import org.override.quickness.feature.home.shop.screen.ShopViewModel
import org.override.quickness.feature.start.screen.StartViewModel

val viewModelModulesStart: Module
    get() = module {
        viewModelOf(::StartViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::NavigationViewModel)
        viewModelOf(::CameraViewModel)
    }

val viewModelModulesHome: Module
    get() = module {
        viewModelOf(::QrViewModel)
        viewModelOf(::WidgetsViewModel)
        viewModelOf(::SettingsViewModel)
        viewModelOf(::ShopViewModel)
        viewModelOf(::CamAnalyzerViewModel)
    }

val viewModelModulesSettings: Module
    get() = module {
        viewModelOf(::SettingsAccountViewModel)
        viewModelOf(::PrivacySettingsViewModel)
        viewModelOf(::QrSettingsViewModel)
        viewModelOf(::DisplaySettingsViewModel)
    }

val viewModelModulesService: Module
    get() = module {
        viewModelOf(::LyraViewModel)
        viewModelOf(::EvaViewModel)
    }