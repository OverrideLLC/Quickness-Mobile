package org.override.quickness.feature.start.screen

import androidx.lifecycle.ViewModel
import org.override.quickness.shared.resources.interfaces.Resources
import org.override.quickness.shared.resources.interfaces.ResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.compose.resources.DrawableResource

class StartViewModel(private val resources: Resources) : ViewModel(), ResourcesProvider {
    data class StartState(
        val name: String = "",
        var bottomLogin: Boolean = false,
        var bottomRegister: Boolean = false
    )

    private val _state = MutableStateFlow(StartState())
    val state: StateFlow<StartState> = _state.asStateFlow()

    fun update(update: StartState.() -> StartState) {
        _state.value = update(_state.value)
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}