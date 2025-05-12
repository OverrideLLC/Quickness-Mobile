package org.override.quickness.network.api.service

import dev.shreyaspatil.ai.client.generativeai.Chat

interface GeminiService {
    suspend fun chat(): Chat
}