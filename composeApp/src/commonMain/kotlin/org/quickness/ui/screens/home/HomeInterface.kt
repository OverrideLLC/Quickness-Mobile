package org.quickness.ui.screens.home

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.datetime.LocalDateTime

interface HomeInterface {
    fun getTokens()
    fun shouldRequestTokens(current: LocalDateTime, lastRequest: LocalDateTime): Boolean
    suspend fun convertTokensToBitmaps(tokens: Map<String, String>)
    fun generatePlaceholderBitmap(): ImageBitmap
}