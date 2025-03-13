package com.feature.home.settings.screen

import androidx.lifecycle.ViewModel
import com.feature.home.settings.enums.SettingsEnum
import com.shared.resources.interfaces.Resources
import com.shared.resources.interfaces.ResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.resources.DrawableResource

class SettingsViewModel(private val resources: Resources) : ViewModel(), ResourcesProvider {
    private val _settingsEnum = MutableStateFlow(SettingsEnum)
    val settingsEnum = _settingsEnum
    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}