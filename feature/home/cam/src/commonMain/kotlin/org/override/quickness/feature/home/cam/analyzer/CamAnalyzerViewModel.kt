package org.override.quickness.feature.home.cam.analyzer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class CamAnalyzerViewModel : ViewModel() {

    private val _state = MutableStateFlow(CamAnalyzerState())
    val state = _state
        .onStart { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CamAnalyzerState()
        )

    fun onAction(action: CamAnalyzerAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}