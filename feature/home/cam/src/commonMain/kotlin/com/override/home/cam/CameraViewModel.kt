package com.override.home.cam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shared.resources.interfaces.Resources
import com.shared.resources.interfaces.ResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.jetbrains.compose.resources.DrawableResource

class CameraViewModel(private val resources: Resources) : ViewModel(), ResourcesProvider {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CameraState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CameraState()
        )

    fun onAction(action: CameraActions) {
        when (action) {
            is CameraActions.OnCompleteScan -> {

            }
        }
    }

    fun update(update: CameraState.() -> CameraState) {
        _state.value = _state.value.update()
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}