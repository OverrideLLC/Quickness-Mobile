package org.override.quickness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.override.quickness.data.api.repository.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.override.quickness.shared.utils.objects.KeysCache.IS_DARK_THEME_KEY

class AppViewModel(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.getBoolean(
                key = IS_DARK_THEME_KEY,
                defaultValue = false
            )?.collect { newValue ->
                _isDarkTheme.value = newValue
            }
        }
    }
}