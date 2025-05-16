package org.override.quickness.feature.home.service.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.jetbrains.compose.resources.DrawableResource
import org.override.quickness.feature.home.service.utils.Widget
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.resources.interfaces.Resources
import org.override.quickness.shared.resources.interfaces.ResourcesProvider
import org.override.quickness.shared.utils.routes.RoutesWidget
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class WidgetsViewModel(
    private val resources: Resources
) : ViewModel(), ResourcesProvider {
    private val _state = MutableStateFlow(WidgetsState())
    val state = _state
        .onStart { loadData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WidgetsState()
        )

    fun onAction(action: WidgetsAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun loadData(){
        _state.value = _state.value.copy(
            widgets = listOf(
                Widget(
                    id = Uuid.random().toString(),
                    icon = getDrawable(ResourceNameKey.LOGO_GAIA_SB.name),
                    name = "Lyra",
                    route = RoutesWidget.Lyra.route
                ),
                Widget(
                    id = Uuid.random().toString(),
                    icon = getDrawable(ResourceNameKey.TT_LOGO.name),
                    name = "TaskTec",
                    route = RoutesWidget.TaskTec.route
                ),
                Widget(
                    id = Uuid.random().toString(),
                    icon = getDrawable(ResourceNameKey.OVERRIDE_LOGO.name),
                    name = "MindStack",
                    route = RoutesWidget.MindStack.route
                )
            )
        )
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}