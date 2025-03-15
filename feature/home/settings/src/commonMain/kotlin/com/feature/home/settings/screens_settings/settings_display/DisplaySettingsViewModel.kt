package com.feature.home.settings.screens_settings.settings_display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.repository.DataStoreRepository
import com.quickness.shared.utils.objects.KeysCache.IS_DARK_THEME_KEY
import com.shared.resources.interfaces.Resources
import com.shared.resources.interfaces.ResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource

class DisplaySettingsViewModel(
    private val dataStoreRepository: DataStoreRepository,
    private val resources: Resources
) : ViewModel(), ResourcesProvider {
    data class DisplaySettingsState(
        var isDarkTheme: Boolean = false,
    )

    private val _state = MutableStateFlow(DisplaySettingsState())
    val state = _state.asStateFlow()

    init {
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