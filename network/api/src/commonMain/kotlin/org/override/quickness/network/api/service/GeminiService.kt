package org.override.quickness.network.api.service

import dev.shreyaspatil.ai.client.generativeai.Chat

interface GeminiService {
    suspend fun generate(prompt: String): String
    suspend fun startChat(): Chat
    suspend fun sendMessage(chat: Chat, message: String): String
    suspend fun generateAdvancedPrompt(prompt: String): String
}