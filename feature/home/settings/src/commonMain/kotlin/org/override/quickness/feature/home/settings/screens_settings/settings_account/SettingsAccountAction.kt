package org.override.quickness.feature.home.settings.screens_settings.settings_account

sealed interface SettingsAccountAction {
    object OnOpen : SettingsAccountAction
    object Logout : SettingsAccountAction
    object OnCloseDialog : SettingsAccountAction
    object OnOpenDialog : SettingsAccountAction
}