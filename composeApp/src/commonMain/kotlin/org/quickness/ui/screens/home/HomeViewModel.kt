package org.quickness.ui.screens.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.quickness.SharedPreference
import org.quickness.data.repository.TokensRepository
import org.quickness.interfaces.QRCodeGenerator
import org.quickness.utils.`object`.KeysCache.FORMAT_KEY
import org.quickness.utils.`object`.KeysCache.LAST_REQUEST_KEY
import org.quickness.utils.`object`.KeysCache.QR_BACKGROUND_KEY
import org.quickness.utils.`object`.KeysCache.QR_COLOR_KEY
import org.quickness.utils.`object`.KeysCache.TOKENS_BITMAP_KEY
import org.quickness.utils.`object`.KeysCache.TOKENS_KEY
import org.quickness.utils.`object`.KeysCache.UID_KEY

class HomeViewModel(
    private val tokensRepository: TokensRepository,
    private val qrCodeGenerator: QRCodeGenerator,
    private val sharedPreference: SharedPreference
) : ViewModel() {
    companion object {
        const val MIN_REQUEST_HOUR = 0 // Hora de reinicio (medianoche)
    }

    fun getTokens() {
        val uid = sharedPreference.getString(UID_KEY, "")
        viewModelScope.launch(Dispatchers.IO) {
            val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val lastRequestDateString =
                sharedPreference.getString(key = LAST_REQUEST_KEY, defaultValue = null)
            val lastRequestDate = try {
                lastRequestDateString.let { LocalDateTime.parse(it) }
            } catch (e: Exception) {
                null
            }

            if (
                lastRequestDate == null
                || shouldRequestTokens(currentTime, lastRequestDate)
                || sharedPreference.getMap(TOKENS_KEY)?.isEmpty() == true
            ) {
                val tokens = tokensRepository.getTokens(uid)
                println("Tokens fetched: $tokens")

                // Guardar tokens en preferencias
                sharedPreference.setMap(TOKENS_KEY, tokens.tokens)

                // Actualizar fecha de última solicitud
                sharedPreference.setString(LAST_REQUEST_KEY, currentTime.toString())

                // Convertir tokens a mapas de bits
                convertTokensToBitmaps(tokens.tokens)
            } else {
                println("Tokens already fetched today.")
            }
        }
    }

    private fun shouldRequestTokens(current: LocalDateTime, lastRequest: LocalDateTime): Boolean {
        val isNewDay = current.date > lastRequest.date
        val isAfterMinHour = current.hour >= MIN_REQUEST_HOUR
        return isNewDay || (isNewDay && isAfterMinHour)
    }

    private suspend fun convertTokensToBitmaps(tokens: Map<String, String>) {
        withContext(Dispatchers.IO) {
            val bitmaps = mutableMapOf<String, ImageBitmap>() // Mapa para almacenar resultados

            // Generar un bitmap placeholder inicial para todos los tokens
            tokens.keys.forEach { key ->
                bitmaps[key] = generatePlaceholderBitmap() // Inicializar con marcador de posición
            }
            sharedPreference.setBitmap(TOKENS_BITMAP_KEY, bitmaps.toMap()) // Guardar inicialización

            // Calcular el token actual basado en los 10 minutos concurrentes
            val totalTokens = 144
            val minutesPerToken = 10
            val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val minutesSinceStartOfDay = currentTime.hour * 60 + currentTime.minute
            val currentTokenIndex = (minutesSinceStartOfDay / minutesPerToken) % totalTokens

            // Obtener la clave del token actual
            val sortedKeys = tokens.keys.sortedBy { it.toIntOrNull() ?: 0 }
            val currentKey = sortedKeys.getOrNull(currentTokenIndex)

            // Generar primero el token actual
            if (currentKey != null) {
                val bitmap = qrCodeGenerator.generateQRCode(
                    data = tokens[currentKey] ?: "",
                    format = sharedPreference.getBoolean(FORMAT_KEY, true),
                    colorBackground = sharedPreference.getInt(QR_COLOR_KEY, Color.Black.toArgb()),
                    colorMapBits = sharedPreference.getInt(QR_BACKGROUND_KEY, Color.White.toArgb())
                )
                bitmaps[currentKey] = bitmap
                sharedPreference.setBitmap(TOKENS_BITMAP_KEY, bitmaps.toMap())
            }

            // Generar los demás tokens en paralelo
            tokens.filterKeys { it != currentKey }.forEach { (key, token) ->
                val bitmap = qrCodeGenerator.generateQRCode(
                    data = token,
                    format = sharedPreference.getBoolean(FORMAT_KEY, true),
                    colorBackground = sharedPreference.getInt(QR_COLOR_KEY, Color.Black.toArgb()),
                    colorMapBits = sharedPreference.getInt(QR_BACKGROUND_KEY, Color.White.toArgb())
                )
                bitmaps[key] = bitmap
                sharedPreference.setBitmap(TOKENS_BITMAP_KEY, bitmaps.toMap())
            }
        }
    }

    private fun generatePlaceholderBitmap(): ImageBitmap = ImageBitmap(1, 1)
}