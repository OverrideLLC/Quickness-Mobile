package org.quickness.ui.screens.home.settings.screens.settings_qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.quickness.interfaces.repository.data.DataStoreRepository
import org.quickness.interfaces.viewmodels.QrSettingsInterface
import org.quickness.utils.options.qr.ColorQrOptions
import org.quickness.utils.options.qr.FormatQrOptions
import org.quickness.utils.options.qr.QrOptionsKeys.FORMAT_KEY
import org.quickness.utils.options.qr.QrOptionsKeys.QR_COLOR_KEY
import org.quickness.utils.options.qr.QrOptionsKeys.ROUNDED_QR_KEY
import org.quickness.utils.options.qr.RoundedQrOptions
import org.quickness.ui.states.QrSettingsState

class QrSettingsViewModel(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel(), QrSettingsInterface {
    private val _state = MutableStateFlow(QrSettingsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val format = dataStoreRepository.getString(
                key = FORMAT_KEY,
                defaultValue = FormatQrOptions.Low.option
            ) ?: FormatQrOptions.Low.option
            val colorQr = dataStoreRepository.getString(
                key = QR_COLOR_KEY,
                defaultValue = ColorQrOptions.Black.option
            ) ?: ColorQrOptions.Black.option
            val rounded = dataStoreRepository.getString(
                key = ROUNDED_QR_KEY,
                defaultValue = RoundedQrOptions.Rounded.option
            ) ?: RoundedQrOptions.Rounded.option
            updateState {
                copy(
                    format = format,
                    colorQr = colorQr,
                    rounded = rounded,
                )
            }
        }
    }

    private fun updateState(update: QrSettingsState.() -> QrSettingsState) {
        _state.value = _state.value.update()
    }

    override fun toggleFormat() {
        viewModelScope.launch {
            val format = if (_state.value.format == FormatQrOptions.High.option) {
                updateState { copy(format = FormatQrOptions.Low.option) }
                FormatQrOptions.Low.option
            } else {
                updateState { copy(format = FormatQrOptions.High.option) }
                FormatQrOptions.High.option
            }
            dataStoreRepository.saveString(mapOf(FORMAT_KEY to format))
        }
    }

    override fun toggleColor(
        colorQr: ColorQrOptions,
    ) {
        viewModelScope.launch {
            updateState {
                copy(
                    colorQr = colorQr.option
                )
            }
            dataStoreRepository.saveString(mapOf(QR_COLOR_KEY to colorQr.option))
        }
    }

    override fun toggleRounded(rounded: RoundedQrOptions) {
        viewModelScope.launch {
            updateState {
                copy(
                    rounded = rounded.option
                )
            }
            dataStoreRepository.saveString(mapOf(ROUNDED_QR_KEY to rounded.option))
        }
    }
}