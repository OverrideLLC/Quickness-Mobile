package org.override.quickness.feature.home.service.screen

import androidx.compose.runtime.*
import org.override.quickness.feature.home.service.utils.Widget

@Immutable
data class WidgetsState(
    val isLoading: Boolean = false,
    val widgets: List<Widget> = emptyList(),
)