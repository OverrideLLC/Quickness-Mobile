package org.override.quickness.feature.home.service.eva

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.*

@Immutable
data class EvaState(
    val isLoading: Boolean = false,
    val textFieldState: TextFieldState = TextFieldState(),
    val isError: Boolean = false,
    val chatActive: Boolean = false,
)