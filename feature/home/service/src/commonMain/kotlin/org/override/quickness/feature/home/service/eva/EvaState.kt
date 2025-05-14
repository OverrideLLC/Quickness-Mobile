package org.override.quickness.feature.home.service.eva

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.*
import dev.shreyaspatil.ai.client.generativeai.Chat
import org.override.quickness.feature.home.service.eva.EvaViewModel.Message

@Immutable
data class EvaState(
    val isLoading: Boolean = false,
    val textFieldState: TextFieldState = TextFieldState(),
    val isError: Boolean = false,
    val chat: Chat? = null,
    val chatActive: Boolean = false,
    val messageError: String? = null,
    val messages: List<Message> = emptyList(),
    val isLoadingMessages: Boolean = false
)