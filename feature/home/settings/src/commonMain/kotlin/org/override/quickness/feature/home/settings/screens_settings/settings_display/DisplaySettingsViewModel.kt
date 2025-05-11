package org.override.quickness.feature.home.settings.screens_settings.settings_display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.override.quickness.data.api.repository.DataStoreRepository
import org.jetbrains.compose.resources.DrawableResource
import org.override.quickness.shared.resources.interfaces.Resources
import org.override.quickness.shared.resources.interfaces.ResourcesProvider
import org.override.quickness.shared.utils.objects.KeysCache.IS_DARK_THEME_KEY

class DisplaySettingsViewModel(
    private val dataStoreRepository: DataStoreRepository,
    private val resources: Resources
) : ViewModel(), ResourcesProvider {
    data class DisplaySettingsState(
        var isDarkTheme: Boolean = false,
    )

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
        .onStart {
            loadState()
            _loading.value = true
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = false
        )

    private val _state = MutableStateFlow(DisplaySettingsState())
    val state = _state.asStateFlow()

    private fun loadState() {
        viewModelScope.launch {
            dataStoreRepository.getBoolean(
                key = IS_DARK_THEME_KEY,
                defaultValue = false
            )?.collect { newValue ->
                _state.value = _state.value.copy(
                    isDarkTheme = newValue
                )
            }
        }
    }

    fun toggleDarkTheme() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isDarkTheme = !_state.value.isDarkTheme)
            dataStoreRepository.saveBoolean(mapOf(IS_DARK_THEME_KEY to _state.value.isDarkTheme))
        }.invokeOnCompletion {
            println("cambio el tema")
        }
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}