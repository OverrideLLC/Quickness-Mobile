package org.quickness.ui.screens.home.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.quickness.SharedPreference
import org.quickness.interfaces.viewmodels.SettingsInterface
import org.quickness.utils.enums.Settings

class SettingsViewModel(
    private val sharedPreference: SharedPreference,
) : ViewModel(), SettingsInterface {
    private val _settingsEnum = MutableStateFlow(Settings)
    val settingsEnum = _settingsEnum
}