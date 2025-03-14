package com.feature.home.settings.screens_settings.settings_qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.repository.DataStoreRepository
import com.feature.home.settings.state.QrSettingsState
import com.quickness.shared.utils.qr_options.ColorQrOptions
import com.quickness.shared.utils.qr_options.FormatQrOptions
import com.quickness.shared.utils.qr_options.QrOptionsKeys
import com.quickness.shared.utils.qr_options.RoundedQrOptions
import com.shared.resources.interfaces.Resources
import com.shared.resources.interfaces.ResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource

class QrSettingsViewModel(
    private val dataStoreRepository: DataStoreRepository,
    private val resources: Resources
) : ViewModel(), ResourcesProvider {
    private val _state = MutableStateFlow(QrSettingsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val format = dataStoreRepository.getString(
                key = QrOptionsKeys.FORMAT_KEY,
                defaultValue = FormatQrOptions.Low.option
            ) ?: FormatQrOptions.Low.option
            val colorQr = dataStoreRepository.getString(
                key = QrOptionsKeys.QR_COLOR_KEY,
                defaultValue = ColorQrOptions.Black.option
            ) ?: ColorQrOptions.Black.option
            val rounded = dataStoreRepository.getString(
                key = QrOptionsKeys.ROUNDED_QR_KEY,
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

    fun toggleFormat() {
        viewModelScope.launch {
            val format = if (_state.value.format == FormatQrOptions.High.option) {
                updateState { copy(format = FormatQrOptions.Low.option) }
                FormatQrOptions.Low.option
            } else {
                updateState { copy(format = FormatQrOptions.High.option) }
                FormatQrOptions.High.option
            }
            dataStoreRepository.saveString(mapOf(QrOptionsKeys.FORMAT_KEY to format))
        }
    }

    fun toggleColor(
        colorQr: ColorQrOptions,
    ) {
        viewModelScope.launch {
            updateState {
                copy(
                    colorQr = colorQr.option
                )
            }
            dataStoreRepository.saveString(mapOf(QrOptionsKeys.QR_COLOR_KEY to colorQr.option))
        }
    }

    fun toggleRounded(rounded: RoundedQrOptions) {
        viewModelScope.launch {
            updateState {
                copy(
                    rounded = rounded.option
                )
            }
            dataStoreRepository.saveString(mapOf(QrOptionsKeys.ROUNDED_QR_KEY to rounded.option))
        }
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}