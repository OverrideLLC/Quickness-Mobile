package org.override.quickness.feature.home.service.eva

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.override.quickness.network.api.repository.GeminiRepository
import org.override.quickness.shared.resources.interfaces.Resources
import org.override.quickness.shared.resources.interfaces.ResourcesProvider
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class EvaViewModel(
    private val resources: Resources,
    private val geminiRepository: GeminiRepository
) : ViewModel(), ResourcesProvider {

    @Immutable
    data class Message @OptIn(ExperimentalUuidApi::class) constructor(
        val id: String = Uuid.random().toString(),
        val text: String,
        val isUser: Boolean,
    )

    private val _state = MutableStateFlow(EvaState())
    val state = _state
        .onStart { startChat() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = EvaState()
        )

    fun onAction(action: EvaAction) {
        when (action) {
            EvaAction.SendMessage -> sendMessage()
        }
    }

    private fun startChat() {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }
        runCatching {
            viewModelScope.launch(Dispatchers.IO) {
                val chat = geminiRepository.startChat()
                _state.update { currentState ->
                    currentState.copy(
                        chat = chat,
                    )
                }
            }
        }.onFailure {
            _state.update { currentState ->
                currentState.copy(
                    isError = true,
                    isLoading = false
                )
            }
        }
    }

    private fun sendMessage() {
        _state.update { currentState ->
            currentState.copy(
                isLoadingMessages = true,
                chatActive = true,
                messages = currentState.messages + Message(
                    text = currentState.textFieldState.text.toString(),
                    isUser = true,
                ),
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _state.value.chat?.let { chat ->
                    geminiRepository.sendMessage(
                        message = _state.value.textFieldState.text.toString(),
                        chat = chat
                    )
                } ?: run {
                    startChat()
                    throw Exception("Chat not found")
                }
            }.onSuccess { response ->
                _state.update { currentState ->
                    currentState.copy(
                        messages = currentState.messages + Message(
                            text = response,
                            isUser = false,
                        ),
                        messageError = null,
                        textFieldState = TextFieldState(),
                        isLoadingMessages = false
                    )
                }
            }.onFailure { failure ->
                _state.update { currentState ->
                    currentState.copy(
                        isError = true,
                        messageError = currentState.messageError,
                        isLoadingMessages = false
                    )
                }
                failure.printStackTrace()
            }
        }
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }

    fun onValueChange(text: String) {
        _state.update { currentState ->
            currentState.copy(
                textFieldState = TextFieldState(text)
            )
        }
    }
}