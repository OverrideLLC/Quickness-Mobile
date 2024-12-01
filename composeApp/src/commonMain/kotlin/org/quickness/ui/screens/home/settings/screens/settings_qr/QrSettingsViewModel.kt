package org.quickness.ui.screens.home.settings.screens.settings_qr

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.quickness.SharedPreference
import org.quickness.interfaces.QRCodeGenerator
import org.quickness.utils.`object`.KeysCache.FORMAT_KEY
import org.quickness.utils.`object`.KeysCache.QR_BACKGROUND_KEY
import org.quickness.utils.`object`.KeysCache.QR_COLOR_KEY
import org.quickness.utils.`object`.KeysCache.QR_TAG_KEY
import org.quickness.utils.`object`.KeysCache.TOKENS_BITMAP_KEY
import org.quickness.utils.`object`.KeysCache.TOKENS_KEY

class QrSettingsViewModel(
    private val sharedPreference: SharedPreference,
    private val qrCodeGenerator: QRCodeGenerator
) : ViewModel(), QrSettingsInterface {
    private val _state = MutableStateFlow(QrSettingsState(sharedPreference))
    val state = _state.asStateFlow()

    private fun updateState(update: QrSettingsState.() -> QrSettingsState) {
        _state.value = _state.value.update()
    }

    override fun toggleFormat() {
        updateState { copy(format = !format, isLoadings = true) }
        sharedPreference.setBoolean(FORMAT_KEY, _state.value.format)

        // Reinicia los tokens después de cambiar el formato
        restartTokens(
            onSuccessfulRestart = { success ->
                updateState { copy(isLoadings = !success) }
            },
            onFailedRestart = {
                updateState { copy(isLoadings = false) }
            }
        )
    }

    override fun toggleColor(colorQr: Int, colorBackground: Int, colorTag: String) {
        updateState {
            copy(
                colorQr = colorQr,
                colorBackground = colorBackground,
                isLoadings = true,
                colorTag = colorTag
            )
        }
        sharedPreference.setInt(QR_COLOR_KEY, colorQr)
        sharedPreference.setInt(QR_BACKGROUND_KEY, colorBackground)
        sharedPreference.setString(QR_TAG_KEY, colorTag)
        restartTokens(
            onSuccessfulRestart = { success ->
                updateState { copy(isLoadings = !success) }
            },
            onFailedRestart = {
                updateState { copy(isLoadings = false) }
            }
        )
    }

    override fun restartTokens(
        onSuccessfulRestart: (Boolean) -> Unit,
        onFailedRestart: () -> Unit
    ) {
        viewModelScope.launch {
            sharedPreference.setBitmap(TOKENS_BITMAP_KEY, emptyMap())
            withContext(Dispatchers.IO) {
                val tokens = sharedPreference.getMap(TOKENS_KEY) ?: emptyMap()
                val bitmaps = mutableMapOf<String, ImageBitmap>() // Mapa para almacenar resultados

                // Generar un bitmap placeholder inicial para todos los tokens
                tokens.keys.forEach { key ->
                    bitmaps[key] =
                        generatePlaceholderBitmap() // Inicializar con marcador de posición
                }
                sharedPreference.setBitmap(
                    TOKENS_BITMAP_KEY,
                    bitmaps.toMap()
                ) // Guardar inicialización

                // Calcular el token actual basado en los 10 minutos concurrentes
                val totalTokens = 144
                val minutesPerToken = 10
                val currentTime =
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
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
                        colorBackground = _state.value.colorBackground,
                        colorMapBits = _state.value.colorQr
                    )
                    bitmaps[currentKey] = bitmap
                    sharedPreference.setBitmap(TOKENS_BITMAP_KEY, bitmaps.toMap())
                    onSuccessfulRestart(true)
                }

                // Generar los demás tokens en paralelo
                tokens.filterKeys { it != currentKey }.forEach { (key, token) ->
                    val bitmap = qrCodeGenerator.generateQRCode(
                        data = token,
                        format = sharedPreference.getBoolean(FORMAT_KEY, true),
                        colorBackground = _state.value.colorBackground,
                        colorMapBits = _state.value.colorQr
                    )
                    bitmaps[key] = bitmap
                    sharedPreference.setBitmap(TOKENS_BITMAP_KEY, bitmaps.toMap())
                }
            }
        }
    }

    override fun generatePlaceholderBitmap(): ImageBitmap = ImageBitmap(1, 1)
}