package org.override.quickness.feature.home.settings.screens_settings.settings_account

import androidx.compose.runtime.*

@Immutable
data class SettingsAccountState(
    val isLoading: Boolean = false,
    val uid: String = "",
    val onOpen: Boolean = false,
    val dialogOpen: Boolean = false
)