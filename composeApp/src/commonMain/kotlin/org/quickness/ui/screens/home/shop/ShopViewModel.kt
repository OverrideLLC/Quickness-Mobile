package org.quickness.ui.screens.home.shop

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShopViewModel : ViewModel() {
    private val _state: MutableStateFlow<ShopState> = MutableStateFlow(ShopState())
    val state: StateFlow<ShopState> = _state.asStateFlow()

    fun update(update: ShopState.() -> ShopState) {
        _state.value = _state.value.update()
    }
}