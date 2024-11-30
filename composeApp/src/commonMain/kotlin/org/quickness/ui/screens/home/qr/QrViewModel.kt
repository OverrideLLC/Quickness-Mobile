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
) : ViewModel() {
    fun generateQRCode(): ImageBitmap? {
        val bitmaps: Map<String, ImageBitmap>? = sharedPreference.getBitmap(TOKENS_BITMAP_KEY)
        val totalTokens = 144
        val minutesPerToken = 10

        // Verificar si hay tokens almacenados
        if (bitmaps.isNullOrEmpty()) {
            println("No tokens available in SharedPreference")
            return null
        }

        // Obtener el tiempo actual
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        // Calcular minutos desde medianoche manualmente
        val minutesSinceStartOfDay = currentTime.hour * 60 + currentTime.minute

        // Calcular el índice del token
        val tokenIndex = (minutesSinceStartOfDay / minutesPerToken) % totalTokens

        // Obtener el token correspondiente
        val tokenKey = bitmaps.keys.sorted()[tokenIndex]
        return bitmaps[tokenKey]
    }
}