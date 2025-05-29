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
import org.override.quickness.feature.eva.utils.toByteArray
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
        EvaService(id = "Lyra", name = "@Lyra", description = "Nutrition assistant"),
        EvaService(id = "TaskTec", name = "@TaskTec", description = "Education assistant"),
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
                _state.update { currentState -> currentState.copy(cameraVisible = !this.state.value.cameraVisible) }
            }

            is EvaAction.SelectImage -> {
                _state.update { currentState -> currentState.copy(imageSelected = action.image) }
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
                _state.value.imageSelected?.let { image ->
                    val imageByteArray = image.toByteArray(format = "PNG", quality = 100)!!
                    val response = geminiRepository.sendMessageWithImage(
                        message = message,
                        chat = chat,
                        image = imageByteArray
                    )
                    _state.update { currentState ->
                        currentState.copy(
                            messages = currentState.messages + Message(
                                text = response,
                                isUser = false
                            ),
                            messageError = null,
                            isLoadingMessages = false,
                            imageSelected = null
                        )
                    }
                    return@launch
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
            when (serviceCall.id) {
                "TaskTec" -> executeApollo(params)
                "Lyra" -> executeLyra(params)
                else -> null
            }
            return true
        }
        return false
    }

    private fun executeLyra(params: String): String {
        var response: String? = null
        val lyraPromptTemplate = """
        Responde preguntas (alimentos, porciones, sustituciones, imágenes) de forma clara, directa, concisa. Ayúdalo a sus metas (perder peso, energía, hábitos saludables, digestión, más verdura/agua, menos azúcar/ultraprocesados), considerando prediabetes, dislipidemia, posible intolerancia lactosa, estilo de vida.
        Información Clave del Plan y Paciente (JUAN MANUEL MORENO GARCIA):
        Nombre: JUAN MANUEL MORENO GARCIA, 38 años.
        Objetivo Calórico: ~1550-1600 kcal/día.
        Macros: Proteínas ~120g, Grasas ~53g (mono/poliinsaturadas), Carbohidratos ~160g (complejos/integrales).
        Fibra: >25-30 g/día. Hidratación: 2-2.5 L agua/día.
        Condiciones: Obesidad I (IMC 30.1), cintura 95cm (riesgo cardiovascular), prediabetes, dislipidemia, posible intolerancia lactosa, estreñimiento, insuficiencia Vit D.
        Desafíos: Sedentarismo, estrés/comida emocional, poco tiempo cocina, dieta actual desequilibrada, comidas irregulares.
        Preferencias: Pastas, pan, queso, chocolate, aguacate, patatas. Rechaza: Verduras amargas, vísceras, pescados grasos.
        Instrucciones Específicas para Respuestas IA:
        Claridad y Concisión: Directo, lenguaje sencillo. Ej: "Sí.", "No es ideal.", "Prefiere X."
        Basado en Plan: Alinea a objetivos/condiciones. Justifica breve. Ej: "No galletas (afectan glucosa/fibra). Mejor avena."
        Preguntas Alimentos (Texto/Imagen): Respuesta directa ("Sí", "No", "Modera") + justificación. Si "No"/modera, da 1-2 alternativas. Ej (imagen ensalada César): "No, pollo frito/aderezo alto en calorías/grasas. Mejor pollo plancha, aderezo ligero."
        Sustituciones: Ofrece 1-2 alternativas saludables, ajustadas al plan/preferencias. Ej: "Reemplaza pan blanco con integral por fibra."
        Consideraciones Adicionales: Recuerda intolerancia lactosa, prioriza fibra (estreñimiento), opciones rápidas (tiempo/estrés), hidratación.
        Ejemplos de Interacción Esperada:
        JUAN MANUEL: "NutriGuía IA, ¿puedo pasta boloñesa?"
        NutriGuía IA: "Modera porción (1 taza pasta, carne magra, poco queso). Incluye ensalada."
        JUAN MANUEL: "Antojo chocolate, ¿qué hago?"
        NutriGuía IA: "1-2 cuadritos chocolate negro (>70%) o fruta."
        
        este es el prompt: $params
    """.trimIndent()
        return try {
            viewModelScope.launch {
                _state.value.imageSelected?.let {
                    response = geminiRepository.sendMessageWithImage(
                        message = if (!_state.value.isUseLyraServices) lyraPromptTemplate else "Esta consulta es de lyra:  $params",
                        chat = _state.value.chat ?: geminiRepository.startChat(),
                        image = it.toByteArray(format = "PNG", quality = 100)!!
                    )
                    _state.update { currentState ->
                        currentState.copy(
                            messages = currentState.messages + Message(
                                text = response!!,
                                isUser = false,
                                displayType = MessageDisplayType.TEXT
                            ),
                            isLoadingMessages = false,
                            messageError = null,
                            isError = false,
                            showServiceSuggestions = false,
                            serviceSuggestions = emptyList(),
                            currentServiceQuery = "",
                            textFieldState = TextFieldState(),
                            imageSelected = null
                        )
                    }
                } ?: run {
                    response = geminiRepository.sendMessage(
                        message = if (!_state.value.isUseLyraServices) lyraPromptTemplate else "Esta consulta es de lyra:  $params",
                        chat = _state.value.chat ?: geminiRepository.startChat()
                    )
                    _state.update { currentState ->
                        currentState.copy(
                            messages = currentState.messages + Message(
                                text = response!!,
                                isUser = false,
                                displayType = MessageDisplayType.TEXT
                            ),
                            isLoadingMessages = false,
                            messageError = null,
                            isError = false,
                            showServiceSuggestions = false,
                            serviceSuggestions = emptyList(),
                            currentServiceQuery = "",
                            textFieldState = TextFieldState(),
                            imageSelected = null
                        )
                    }
                }
            }
            response ?: ""
        } catch (e: Exception) {
            _state.update { currentState ->
                currentState.copy(
                    messages = currentState.messages + Message(
                        text = "Error al procesar '${1}': ${e.message}",
                        isUser = false
                    ),
                    isLoadingMessages = false,
                    isError = true,
                    messageError = null,
                    showServiceSuggestions = false,
                    serviceSuggestions = emptyList(),
                    currentServiceQuery = "",
                    textFieldState = TextFieldState(),
                    imageSelected = null
                )
            }
            e.printStackTrace()
            response ?: ""
        }
    }

    private fun executeApollo(params: String): String {
        var response: String? = null
        val apolloPromptTemplate = """
            Base de Conocimientos de Cursos:
            1. Matemáticas Discretas (Ingeniería de Software, 3er Semestre, Sección A)
            Trabajo: Tarea 1: Tablas de Verdad (Resolver ejercicios 1.1-1.5 del libro).
            Anuncio: Próximo Examen Parcial (Temas: Lógica Proposicional, Teoría de Conjuntos).
            2. Introducción a la Economía (Administración de Empresas, 1er Año, Sección Única)
            Trabajo: Ensayo: Inflación (1000 palabras sobre causas y consecuencias).
            Anuncio: Conferencia Invitada (Dr. Pérez sobre "Perspectivas Económicas Globales", el viernes).
            3. Biología Celular (Medicina, 2º Semestre, Sección B-1)
            Trabajo: Reporte de Práctica de Mitosis (Detallar fases observadas y dibujar).
            Anuncio: Cambio de Horario Laboratorio (Miércoles ahora de 14-16 hrs).
            4. Historia del Arte Renacentista (Bellas Artes, 5º Semestre, Sección C)
            Trabajo: Análisis Comparativo: Donatello vs. Miguel Ángel (Comparar técnicas y temáticas).
            Anuncio: Visita al Museo Cancelada (Visita al Museo Nacional de Arte del jueves cancelada).
        este es el prompt: $params
    """.trimIndent()
        return try {
            viewModelScope.launch {
                _state.value.imageSelected?.let {
                    response = geminiRepository.sendMessageWithImage(
                        message = if (!_state.value.isUseLyraServices) apolloPromptTemplate else "Esta consulta es de TaskTec:  $params",
                        chat = _state.value.chat ?: geminiRepository.startChat(),
                        image = it.toByteArray(format = "PNG", quality = 100)!!
                    )
                    _state.update { currentState ->
                        currentState.copy(
                            messages = currentState.messages + Message(
                                text = response!!,
                                isUser = false,
                                displayType = MessageDisplayType.TEXT
                            ),
                            isLoadingMessages = false,
                            messageError = null,
                            isError = false,
                            showServiceSuggestions = false,
                            serviceSuggestions = emptyList(),
                            currentServiceQuery = "",
                            textFieldState = TextFieldState(),
                            imageSelected = null
                        )
                    }
                } ?: run {
                    response = geminiRepository.sendMessage(
                        message = if (!_state.value.isUseLyraServices) apolloPromptTemplate else "Esta consulta es de TaskTec:  $params",
                        chat = _state.value.chat ?: geminiRepository.startChat()
                    )
                    _state.update { currentState ->
                        currentState.copy(
                            messages = currentState.messages + Message(
                                text = response!!,
                                isUser = false,
                                displayType = MessageDisplayType.TEXT
                            ),
                            isLoadingMessages = false,
                            messageError = null,
                            isError = false,
                            showServiceSuggestions = false,
                            serviceSuggestions = emptyList(),
                            currentServiceQuery = "",
                            textFieldState = TextFieldState(),
                            imageSelected = null
                        )
                    }
                }
            }
            response ?: ""
        } catch (e: Exception) {
            _state.update { currentState ->
                currentState.copy(
                    messages = currentState.messages + Message(
                        text = "Error al procesar '${1}': ${e.message}",
                        isUser = false
                    ),
                    isLoadingMessages = false,
                    isError = true,
                    messageError = null,
                    showServiceSuggestions = false,
                    serviceSuggestions = emptyList(),
                    currentServiceQuery = "",
                    textFieldState = TextFieldState(),
                    imageSelected = null
                )
            }
            e.printStackTrace()
            response ?: ""
        }
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