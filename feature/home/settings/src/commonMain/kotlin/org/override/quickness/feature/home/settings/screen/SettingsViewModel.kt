package org.override.quickness.feature.home.settings.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.resources.DrawableResource
import org.override.quickness.feature.home.settings.enums.SettingsEnum
import org.override.quickness.shared.resources.interfaces.Resources
import org.override.quickness.shared.resources.interfaces.ResourcesProvider

class SettingsViewModel(private val resources: Resources) : ViewModel(), ResourcesProvider {
    private val _settingsEnum = MutableStateFlow(SettingsEnum)
    val settingsEnum = _settingsEnum
    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}