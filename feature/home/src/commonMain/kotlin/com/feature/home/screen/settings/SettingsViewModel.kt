package com.feature.home.screen.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.quickness.interfaces.viewmodels.SettingsInterface
import org.quickness.utils.Settings

class SettingsViewModel() : ViewModel(), SettingsInterface {
    private val _settingsEnum = MutableStateFlow(Settings)
    val settingsEnum = _settingsEnum
}