package org.override.quickness.feature.home.settings.screens_settings.settings_privacy

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.override.quickness.feature.home.settings.state.PrivacySettingsState

class PrivacySettingsViewModel() : ViewModel() {
    private val _state = MutableStateFlow(PrivacySettingsState())
    val state = _state.asStateFlow()

    fun update(update: PrivacySettingsState.() -> PrivacySettingsState) {
        _state.value = _state.value.update()
    }
}