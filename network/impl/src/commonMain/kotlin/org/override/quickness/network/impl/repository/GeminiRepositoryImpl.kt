package org.override.quickness.network.impl.repository

import dev.shreyaspatil.ai.client.generativeai.Chat
import org.override.quickness.network.api.repository.GeminiRepository
import org.override.quickness.network.api.service.GeminiService

class GeminiRepositoryImpl(
    private val service: GeminiService
): GeminiRepository {
    override suspend fun generate(prompt: String): String {
        val finalPrompt = prompt
        return service.generate(prompt = finalPrompt)
    }

    override suspend fun startChat(): Chat {
        return service.startChat()
    }

    override suspend fun sendMessage(
        chat: Chat,
        message: String
    ): String {
        return service.sendMessage(
            chat = chat,
            message = message
        )
    }

    override suspend fun generateAdvancedPrompt(prompt: String): String {
        return service.generateAdvancedPrompt(prompt = prompt)
    }
}