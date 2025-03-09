package org.quickness.ui.screens.home.settings.screens.settings_privacy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.impl.repository.ClientRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.quickness.ui.states.PrivacySettingsState

class PrivacySettingsViewModel(
    private val repositoryClient: ClientRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow(PrivacySettingsState())
    val state = _state.asStateFlow()

    fun update(update: PrivacySettingsState.() -> PrivacySettingsState) {
        _state.value = _state.value.update()
    }

    fun downloadData() {
        update { copy(isLoading = true) }
        try {
            viewModelScope.launch {
                if (repositoryClient.downloadUserData().isNotEmpty()) {
                    update { copy(success = true, message = "Data downloaded successfully") }
                } else {
                    update { copy(error = true, message = "Error downloading data") }
                }
            }
        } catch (e: Exception) {
            update { copy(error = true, message = e.message) }
        } finally {
            update { copy(isLoading = false) }
        }
    }
}