package org.override.quickness.feature.home.settings.screens_settings.settings_qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.override.quickness.data.api.repository.DataStoreRepository
import org.override.quickness.feature.home.settings.state.QrSettingsState
import org.override.quickness.shared.resources.interfaces.Resources
import org.override.quickness.shared.resources.interfaces.ResourcesProvider
import org.override.quickness.shared.utils.qr_options.ColorQrOptions
import org.override.quickness.shared.utils.qr_options.FormatQrOptions
import org.override.quickness.shared.utils.qr_options.QrOptionsKeys
import org.override.quickness.shared.utils.qr_options.RoundedQrOptions

class QrSettingsViewModel(
    private val dataStoreRepository: DataStoreRepository,
    private val resources: Resources
) : ViewModel(), ResourcesProvider {

    private val _state = MutableStateFlow(QrSettingsState())
    val state = _state.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()
        .onStart { loadData() }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = false
        )

    private fun loadData() {
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
            _loading.value = true
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