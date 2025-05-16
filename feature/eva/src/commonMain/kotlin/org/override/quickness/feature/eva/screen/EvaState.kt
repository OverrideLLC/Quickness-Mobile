package org.override.quickness.feature.eva.screen

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import dev.shreyaspatil.ai.client.generativeai.Chat
import org.override.quickness.feature.eva.utils.EvaService

@Immutable
data class EvaState(
    val isLoading: Boolean = false,
    val textFieldState: TextFieldState = TextFieldState(),
    val isError: Boolean = false,
    val chat: Chat? = null,
    val chatActive: Boolean = false,
    val messageError: String? = null,
    val messages: List<EvaViewModel.Message> = emptyList(),
    val isLoadingMessages: Boolean = false,
    val isNavigationBarVisible: Boolean = false,
    val serviceSuggestions: List<EvaService> = emptyList(),
    val showServiceSuggestions: Boolean = false,
    val currentServiceQuery: String = "",
    val imageSelected: ImageBitmap? = null,
    val cameraVisible: Boolean = false,
    val isUseLyraServices: Boolean = false
)