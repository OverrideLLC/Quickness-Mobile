package org.override.quickness.feature.home.service.eva

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.*
import dev.shreyaspatil.ai.client.generativeai.Chat
import org.override.quickness.feature.home.service.eva.EvaViewModel.Message
import org.override.quickness.feature.home.service.utils.EvaService

@Immutable
data class EvaState(
    val isLoading: Boolean = false,
    val textFieldState: TextFieldState = TextFieldState(),
    val isError: Boolean = false,
    val chat: Chat? = null,
    val chatActive: Boolean = false,
    val messageError: String? = null,
    val messages: List<Message> = emptyList(),
    val isLoadingMessages: Boolean = false,
    val isNavigationBarVisible: Boolean = false,
    val serviceSuggestions: List<EvaService> = emptyList(),
    val showServiceSuggestions: Boolean = false,
    val currentServiceQuery: String = ""
)