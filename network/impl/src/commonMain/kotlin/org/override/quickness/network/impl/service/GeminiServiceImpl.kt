package org.override.quickness.network.impl.service

import dev.shreyaspatil.ai.client.generativeai.Chat
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import dev.shreyaspatil.ai.client.generativeai.type.Content
import dev.shreyaspatil.ai.client.generativeai.type.content
import dev.shreyaspatil.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.override.quickness.network.api.service.GeminiService

class GeminiServiceImpl : GeminiService {
    private val basePromptTemplate = """
    Eres Eva, la asistente personal inteligente de Override, optimizada para operar dentro de la aplicación Quickness.
    Tu objetivo principal es maximizar la productividad y eficiencia del usuario.
    Tu personalidad es rápida, clara, precisa y adaptativa.
    Responde siempre de forma estructurada, siendo concisa pero con suficiente detalle para ser útil.

    Dentro de Quickness, tu funcionalidad varía según la sección de la app en la que se encuentre el usuario. Toma en cuenta estas especializaciones al responder:

        *   **Lyra (Nutrición):** Si la consulta es sobre Lyra, actúa como un asistente enfocado en nutrición. Tu función es proporcionar datos del cliente (si están disponibles) y ofrecer recomendaciones de alimentos basadas en parámetros nutricionales.
        *   **Apollo (Educación - para Alumnos):** Si la consulta es sobre Apollo (desde la perspectiva del alumno), actúa como un asistente académico. Tu función es mostrar información sobre trabajos y tareas, y ofrecer sugerencias para mejorar el rendimiento académico o los trabajos específicos.

    **Instrucción Clave:**
        Si la solicitud del usuario trata específicamente sobre Lyra o Apollo, *tu respuesta debe centrarse exclusivamente en la información y las funcionalidades descritas anteriormente para ese proyecto dentro de Quickness*. No inventes funcionalidades o información que no estén listadas aquí.
        Para cualquier otra consulta que no sea sobre Lyra o Apollo, responde aplicando tu personalidad general de asistente eficiente.
    """.trimIndent()

    val generativeAiChat: GenerativeModel
        get() = GenerativeModel(
            modelName = "gemini-2.0-flash-001",
            apiKey = "AIzaSyBsLxYDfVlDDIgdOWg4Ke_dFJdGmYA-9Qc",
        )
    val generativeAiQuestions: GenerativeModel
        get() {
            val config = generationConfig {
                responseMimeType = "application/json"
            }
            return GenerativeModel(
                modelName = "gemini-2.0-flash-lite-001",
                apiKey = "AIzaSyBsLxYDfVlDDIgdOWg4Ke_dFJdGmYA-9Qc",
            )
        }

    override suspend fun generate(prompt: String): String {
        return generativeAiQuestions.generateContent(prompt).text ?: "No response"
    }

    override suspend fun startChat(): Chat {
        val promptList: List<Content> = listOf(
            content(role = "user") { text(basePromptTemplate) },
            content(role = "model") { text("Ok") },
        )
        try {
            val chat = generativeAiChat
                .startChat(
                    history = promptList
                )
            return chat
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun sendMessage(
        chat: Chat,
        message: String
    ): String {
        return try {
            withContext(Dispatchers.IO) {
                val inputContent = content(role = "user") {
                    if (message.isNotBlank()) {
                        text(message)
                    }
                }

                val response = chat.sendMessage(inputContent)

                response.text ?: "No se recibió texto en la respuesta."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error al enviar mensaje: ${e.message}"
        }
    }

    override suspend fun generateAdvancedPrompt(prompt: String): String {
        return generativeAiChat.generateContent(prompt).text ?: "No response"
    }
}