package org.override.quickness.feature.eva.screen

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
import org.override.quickness.feature.eva.utils.EvaService
import org.override.quickness.network.api.repository.GeminiRepository
import org.override.quickness.shared.resources.interfaces.Resources
import org.override.quickness.shared.resources.interfaces.ResourcesProvider
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class EvaViewModel(
    private val resources: Resources,
    private val geminiRepository: GeminiRepository
) : ViewModel(), ResourcesProvider {

    enum class MessageDisplayType {
        TEXT,
        CUSTOM_COMPONENT
    }

    @Immutable
    data class Message @OptIn(ExperimentalUuidApi::class) constructor(
        val id: String = Uuid.random().toString(),
        val text: String,
        val isUser: Boolean,
        val displayType: MessageDisplayType = MessageDisplayType.TEXT
    )

    private val availableServices = listOf(
        EvaService(id = "Lyra", name = "@Lyra", description = "AI assistant"),
        EvaService(id = "Apollo", name = "@Apollo", description = "AI assistant"),
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
            EvaAction.UpdateNavigationBarVisible -> updateNavigationBarVisible()
            is EvaAction.UpdateTextFieldState -> onValueChange(action.text)
            is EvaAction.SelectService -> selectService(action.service)
            EvaAction.DismissServiceSuggestions -> dismissServiceSuggestions()
            EvaAction.OpenCamera -> {

            }
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
                        isLoading = false
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
        val currentText = _state.value.textFieldState.text.toString().trim()
        if (currentText.isEmpty()) return

        _state.update { currentState ->
            currentState.copy(
                chatActive = true,
                messages = currentState.messages + Message(text = currentText, isUser = true),
                textFieldState = TextFieldState(),
                showServiceSuggestions = false,
                serviceSuggestions = emptyList()
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val serviceHandled = handleServiceCommand(currentText)
            if (!serviceHandled) sendToGemini(currentText)
        }
    }

    private fun sendToGemini(message: String) {
        _state.update { it.copy(isLoadingMessages = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val chat = _state.value.chat ?: geminiRepository.startChat().also { newChat ->
                    _state.update { it.copy(chat = newChat) }
                }

                val response = geminiRepository.sendMessage(message = message, chat = chat)
                _state.update { currentState ->
                    currentState.copy(
                        messages = currentState.messages + Message(text = response, isUser = false),
                        messageError = null,
                        isLoadingMessages = false
                    )
                }
            } catch (e: Exception) {
                _state.update { currentState ->
                    currentState.copy(
                        isError = true,
                        messageError = e.message ?: "Error al contactar a Gemini.",
                        isLoadingMessages = false
                    )
                }
                e.printStackTrace()
            }
        }
    }

    private fun handleServiceCommand(text: String): Boolean {
        val serviceCall =
            availableServices.firstOrNull { text.startsWith(it.name, ignoreCase = true) }
        if (serviceCall != null) {
            val params = text.substringAfter(serviceCall.name).trim()
            _state.update { it.copy(isLoadingMessages = true) }

            try {
                val serviceResult: String? = when (serviceCall.id) {
                    "Apollo" -> executeApollo(params)
                    "Lyra" -> executeLyra(params)
                    else -> null
                }

                if (serviceResult != null) {
                    _state.update { currentState ->
                        currentState.copy(
                            messages = currentState.messages + Message(
                                text = serviceResult,
                                isUser = false,
                                displayType = MessageDisplayType.CUSTOM_COMPONENT
                            ),
                            isLoadingMessages = false,
                            messageError = null
                        )
                    }
                } else if (serviceCall.id != "gemini") {
                    _state.update { currentState ->
                        currentState.copy(
                            messages = currentState.messages + Message(
                                text = "Lo siento, no pude procesar el comando para ${serviceCall.name}.",
                                isUser = false,
                            ),
                            isLoadingMessages = false
                        )
                    }
                }
                return serviceCall.id != "gemini"
            } catch (e: Exception) {
                _state.update { currentState ->
                    currentState.copy(
                        messages = currentState.messages + Message(
                            text = "Error al procesar '${serviceCall.name}': ${e.message}",
                            isUser = false
                        ),
                        isLoadingMessages = false,
                        isError = true
                    )
                }
                e.printStackTrace()
                return true
            }
        }
        return false
    }

    private fun executeLyra(params: String): String {
        return ""
    }

    private fun executeApollo(params: String): String {
        return ""
    }

    private fun onValueChange(text: String) {
        val currentTextFieldState = _state.value.textFieldState
        val newTextFieldState = TextFieldState(text, currentTextFieldState.selection)

        _state.update { currentState ->
            currentState.copy(
                textFieldState = newTextFieldState
            )
        }
        val atMentionRegex = """@([a-zA-Z0-9_]*)$""".toRegex()
        val matchResult = atMentionRegex.find(text)

        if (matchResult != null) {
            val query = matchResult.groupValues[1]
            val filteredServices = availableServices.filter { service ->
                service.name.startsWith("@$query", ignoreCase = true)
            }
            _state.update { currentState ->
                currentState.copy(
                    serviceSuggestions = filteredServices,
                    showServiceSuggestions = filteredServices.isNotEmpty(),
                    currentServiceQuery = query
                )
            }
        } else {
            if (_state.value.showServiceSuggestions) {
                _state.update { currentState ->
                    currentState.copy(
                        serviceSuggestions = emptyList(),
                        showServiceSuggestions = false,
                        currentServiceQuery = ""
                    )
                }
            }
        }
    }

    private fun updateNavigationBarVisible() {
        _state.update { currentState ->
            currentState.copy(
                isNavigationBarVisible = !currentState.isNavigationBarVisible
            )
        }
    }

    private fun selectService(service: EvaService) {
        val currentText = _state.value.textFieldState.text.toString()
        val serviceQuery = _state.value.currentServiceQuery

        val newText = if (currentText.endsWith("@$serviceQuery", ignoreCase = true)) {
            currentText.substring(
                startIndex = 0,
                endIndex = currentText.length - (serviceQuery.length + 1)
            ) + service.name + " "
        } else {
            service.name + " "
        }

        _state.update { currentState ->
            currentState.copy(
                textFieldState = TextFieldState(newText),
                serviceSuggestions = emptyList(),
                showServiceSuggestions = false,
                currentServiceQuery = ""
            )
        }
    }

    private fun dismissServiceSuggestions() {
        _state.update { currentState ->
            currentState.copy(
                serviceSuggestions = emptyList(),
                showServiceSuggestions = false,
                currentServiceQuery = ""
            )
        }
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}