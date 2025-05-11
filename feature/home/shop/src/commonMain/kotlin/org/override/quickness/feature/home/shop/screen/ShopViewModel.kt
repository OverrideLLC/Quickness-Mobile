package org.override.quickness.feature.home.shop.screen

import androidx.lifecycle.ViewModel
import org.override.quickness.feature.home.shop.states.ShopState
import org.override.quickness.shared.resources.interfaces.Resources
import org.override.quickness.shared.resources.interfaces.ResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.compose.resources.DrawableResource

class ShopViewModel(
    private val resources: Resources
) : ViewModel(), ResourcesProvider {
    private val _state: MutableStateFlow<ShopState> = MutableStateFlow(ShopState())
    val state: StateFlow<ShopState> = _state.asStateFlow()

    fun update(update: ShopState.() -> ShopState) {
        _state.value = _state.value.update()
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}