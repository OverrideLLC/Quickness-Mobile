package org.quickness.ui.screens.home.qr

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.quickness.SharedPreference
import org.quickness.utils.`object`.KeysCache.TOKENS_BITMAP_KEY

class QrViewModel(
    private val sharedPreference: SharedPreference
) : ViewModel(), QrInterface {
    override fun generateQRCode(): ImageBitmap? {
        val bitmaps = sharedPreference.getBitmap(TOKENS_BITMAP_KEY) ?: run {
            println("No bitmaps found")
            return null
        }

        val totalTokens = 144
        val minutesPerToken = 10

        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val minutesSinceStartOfDay = currentTime.hour * 60 + currentTime.minute
        println("Current time: $currentTime")
        println("Minutes since start of day: $minutesSinceStartOfDay")

        // Calcular el índice del token
        val tokenIndex = (minutesSinceStartOfDay / minutesPerToken) % totalTokens

        // Verificar que el índice sea válido
        val sortedKeys = bitmaps.keys.sortedBy { it.toIntOrNull() ?: 0 }
        if (tokenIndex >= sortedKeys.size) {
            println("Token index exceeds available tokens")
            return null
        }

        val tokenKey = sortedKeys[tokenIndex]
        println("Token key: $tokenKey")

        return bitmaps[tokenKey]
    }
}