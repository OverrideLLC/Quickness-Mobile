package com.feature.home.settings.screens_settings.settings_privacy

import androidx.lifecycle.ViewModel
import com.feature.home.settings.state.PrivacySettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PrivacySettingsViewModel() : ViewModel() {
    private val _state = MutableStateFlow(PrivacySettingsState())
    val state = _state.asStateFlow()

    fun update(update: PrivacySettingsState.() -> PrivacySettingsState) {
        _state.value = _state.value.update()
    }
}