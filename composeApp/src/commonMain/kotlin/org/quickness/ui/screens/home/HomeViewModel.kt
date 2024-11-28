package org.quickness.ui.screens.home

import androidx.compose.ui.graphics.ImageBitmap
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
import org.quickness.utils.`object`.KeysCache.LAST_REQUEST_KEY
import org.quickness.utils.`object`.KeysCache.TOKENS_BITMAP_KEY
import org.quickness.utils.`object`.KeysCache.TOKENS_KEY

class HomeViewModel(
    private val tokensRepository: TokensRepository,
    private val qrCodeGenerator: QRCodeGenerator,
) : ViewModel() {
    companion object {
        const val MIN_REQUEST_HOUR = 1 // Hora mínima para solicitar claves
    }

    fun getTokens(uid: String, sharedPreference: SharedPreference) {
        viewModelScope.launch(Dispatchers.Default) {
            val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val lastRequestDateString = sharedPreference.getString(key = LAST_REQUEST_KEY, defaultValue = null)
            val lastRequestDate = try {
                lastRequestDateString.let { LocalDateTime.parse(it) }
            } catch (e: Exception) {
                null // Si no es parseable, asumir que no hay una fecha previa
            }

            // Verificar si ya se realizó una solicitud hoy después de la hora mínima
            if (lastRequestDate == null || shouldRequestTokens(currentTime, lastRequestDate)) {
                val tokens = tokensRepository.getTokens(uid)
                println("Tokens fetched: $tokens")

                // Guardar tokens en preferencias
                sharedPreference.setMap(TOKENS_KEY, tokens.tokens)

                // Actualizar fecha de última solicitud
                sharedPreference.setString(LAST_REQUEST_KEY, currentTime.toString())

                // Convertir tokens a mapas de bits
                convertTokensToBitmaps(
                    tokens.tokens,
                    sharedPreference
                )
            } else {
                println("Tokens already fetched today.")
            }
        }
    }

    private fun shouldRequestTokens(current: LocalDateTime, lastRequest: LocalDateTime): Boolean {
        val isSameDay = current.date == lastRequest.date
        val isAfterMinHour = current.hour >= MIN_REQUEST_HOUR
        return !isSameDay || (isSameDay && !isAfterMinHour)
    }

    private suspend fun convertTokensToBitmaps(
        tokens: Map<String, String>,
        sharedPreference: SharedPreference
    ) {
        withContext(Dispatchers.IO) {
            val bitmaps = tokens.mapValues { (_, token) ->
                qrCodeGenerator.generateQRCode(token)
            }
            sharedPreference.setBitmap(TOKENS_BITMAP_KEY, bitmaps)
        }
    }


}
