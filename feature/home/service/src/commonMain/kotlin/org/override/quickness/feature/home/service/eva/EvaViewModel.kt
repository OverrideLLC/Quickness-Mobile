package org.override.quickness.feature.home.service.eva

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.jetbrains.compose.resources.DrawableResource
import org.override.quickness.shared.resources.interfaces.Resources
import org.override.quickness.shared.resources.interfaces.ResourcesProvider

class EvaViewModel(
    private val resources: Resources
) : ViewModel(), ResourcesProvider {

    private val _state = MutableStateFlow(EvaState())
    val state = _state
        .onStart { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = EvaState()
        )

    fun onAction(action: EvaAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }

    fun onValueChange(text: String) {
        _state.value = _state.value.copy(
            textFieldState = TextFieldState(text)
        )
    }
}