package org.quickness.ui.screens.home.settings.screens.settings_privacy

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PrivacySettingsViewModel : ViewModel() {
    data class PrivacySettingsState(
        val showBottomSheetDownload: Boolean = false
    )

    private val _state = MutableStateFlow(PrivacySettingsState())
    val state = _state.asStateFlow()

    fun update(update: PrivacySettingsState.() -> PrivacySettingsState) {
        _state.value = _state.value.update()
    }
}