package com.feature.home.settings.screens_settings.settings_account

import androidx.lifecycle.ViewModel
import com.feature.home.settings.state.AccountState
import com.shared.resources.interfaces.Resources
import com.shared.resources.interfaces.ResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.compose.resources.DrawableResource

class AccountSettingsViewModel(private val resources: Resources) : ViewModel(), ResourcesProvider {
    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    fun update(newState: AccountState.() -> AccountState) {
        _state.value = _state.value.newState()
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}