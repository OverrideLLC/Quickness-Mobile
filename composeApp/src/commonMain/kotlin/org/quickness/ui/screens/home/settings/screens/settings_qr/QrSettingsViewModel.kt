package org.quickness.ui.screens.home.settings.screens.settings_qr

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.quickness.SharedPreference
import org.quickness.interfaces.viewmodels.QrSettingsInterface
import org.quickness.ui.states.QrSettingsState
import org.quickness.utils.`object`.KeysCache.FORMAT_KEY
import org.quickness.utils.`object`.KeysCache.QR_BACKGROUND_KEY
import org.quickness.utils.`object`.KeysCache.QR_COLOR_KEY
import org.quickness.utils.`object`.KeysCache.QR_TAG_KEY
import org.quickness.utils.`object`.KeysCache.ROUNDED_ROUNDED_QR_KEY

class QrSettingsViewModel(
    private val sharedPreference: SharedPreference,
) : ViewModel(), QrSettingsInterface {
    private val _state = MutableStateFlow(QrSettingsState(sharedPreference))
    val state = _state.asStateFlow()

    private fun updateState(update: QrSettingsState.() -> QrSettingsState) {
        _state.value = _state.value.update()
    }

    override fun toggleFormat() {
        updateState { copy(format = !format, isLoadings = true) }
        sharedPreference.setBoolean(FORMAT_KEY, _state.value.format)
    }

    override fun toggleColor(
        colorQr: Int,
        colorBackground: Int,
        colorTag: String,
    ) {
        updateState {
            copy(
                colorQr = colorQr,
                colorBackground = colorBackground,
                colorTag = colorTag,
            )
        }
        sharedPreference.setInt(QR_COLOR_KEY, colorQr)
        sharedPreference.setInt(QR_BACKGROUND_KEY, colorBackground)
        sharedPreference.setString(QR_TAG_KEY, colorTag)
    }

    override fun toggleRounded(rounded: String) {
        updateState { copy(rounded = rounded) }
        sharedPreference.setString(ROUNDED_ROUNDED_QR_KEY, rounded)
    }
}