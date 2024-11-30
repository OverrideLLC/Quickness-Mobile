package org.quickness.ui.screens.home.settings.screens.settings_qr

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class QrSettingsViewModel : ViewModel() {
    data class QrSettingsState(
        val format: Boolean = true,
        val readability: Boolean = false
    )

    private val _state = MutableStateFlow(QrSettingsState())
    val state = _state.asStateFlow()

    fun updateState(update: QrSettingsState.() -> QrSettingsState) {
        _state.value = _state.value.update()
    }
}