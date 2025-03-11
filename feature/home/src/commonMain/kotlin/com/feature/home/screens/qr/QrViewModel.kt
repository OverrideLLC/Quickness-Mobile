package com.feature.home.screens.qr

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.repository.DataStoreRepository
import com.example.api.repository.TokenDatabaseRepository
import com.feature.home.states.QrState
import com.quickness.shared.utils.qr_options.ColorQrOptions
import com.quickness.shared.utils.qr_options.FormatQrOptions
import com.quickness.shared.utils.qr_options.QrOptionsKeys
import com.quickness.shared.utils.qr_options.RoundedQrOptions
import com.shared.resources.interfaces.Resources
import com.shared.resources.interfaces.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import qrgenerator.qrkitpainter.QrKitBallShape
import qrgenerator.qrkitpainter.QrKitBrush
import qrgenerator.qrkitpainter.QrKitColors
import qrgenerator.qrkitpainter.QrKitOptions
import qrgenerator.qrkitpainter.QrKitShapes
import qrgenerator.qrkitpainter.QrPainter
import qrgenerator.qrkitpainter.createRoundCorners
import qrgenerator.qrkitpainter.solidBrush

class QrViewModel(
    private val tokenDatabaseRepository: TokenDatabaseRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val resources: Resources
) : ViewModel(), ResourcesProvider {
    private val _qrState = MutableStateFlow(QrState())
    val qrState = _qrState.asStateFlow()

    fun update(update: QrState.() -> QrState) {
        _qrState.value = _qrState.value.update()
    }

    suspend fun getColors() {
        val colorOption = dataStoreRepository.getString(
            key = QrOptionsKeys.QR_COLOR_KEY,
            defaultValue = ColorQrOptions.Black.option
        )
        println("Color option: $colorOption")
        ColorQrOptions.fromOption(
            colorOption ?: ColorQrOptions.Black.option
        )?.colors?.let { colors ->
            update { copy(colors = colors) }
        }
    }

    fun calculateInterval(): Int {
        val now = Clock.System.now() // Tiempo actual UTC
        val localTime = now.toLocalDateTime(TimeZone.of("UTC-6"))
        val minutesSinceMidnight = localTime.hour * 60 + localTime.minute
        println("Minutes since midnight: $minutesSinceMidnight")
        return (minutesSinceMidnight / 10) % 144 + 1
    }

    fun updateQrCodeForCurrentInterval() {
        viewModelScope.launch {
            val interval = calculateInterval()
            if (_qrState.value.currentInterval == "Interval $interval") return@launch
            val token = tokenDatabaseRepository.getTokenByIndex(interval)?.tokenValue
            println("Token for interval $interval: $token")
            updateQrCodeForToken(token, "Interval $interval")
        }
    }

    fun updateQrCodeForToken(token: String?, interval: String) {
        if (token == _qrState.value.lastQrData && interval == _qrState.value.currentInterval) return
        viewModelScope.launch {
            val format = dataStoreRepository.getString(
                key = QrOptionsKeys.FORMAT_KEY,
                defaultValue = FormatQrOptions.Low.option
            ) ?: FormatQrOptions.Low.option

            val colors = dataStoreRepository.getString(
                key = QrOptionsKeys.QR_COLOR_KEY,
                defaultValue = ColorQrOptions.Black.option
            ).let { colorOption ->
                ColorQrOptions.fromOption(colorOption ?: ColorQrOptions.Black.option)?.colors
                    ?: ColorQrOptions.Black.colors
            }
            val qrKitShapes = dataStoreRepository.getString(
                key = QrOptionsKeys.ROUNDED_QR_KEY,
                defaultValue = RoundedQrOptions.Rounded.option
            ) ?: RoundedQrOptions.Rounded.option
            val qr = withContext(Dispatchers.Default) {
                QrPainter(
                    content = if (token.isNullOrEmpty()) "" else token,
                    config = QrKitOptions(
                        shapes = QrKitShapes(
                            darkPixelShape = RoundedQrOptions.fromOption(qrKitShapes)?.qrKitShapes
                                ?: RoundedQrOptions.Rounded.qrKitShapes,
                            ballShape = QrKitBallShape.createRoundCorners(1f)
                        ),
                        colors = QrKitColors(
                            lightBrush = QrKitBrush.solidBrush(color = Color.Transparent),
                            ballBrush = QrKitBrush.solidBrush(
                                color = Color(colors[0])
                            ),
                            frameBrush = QrKitBrush.solidBrush(
                                color = Color(colors[0])
                            ),
                            darkBrush = QrKitBrush.solidBrush(
                                color = Color(colors[0])
                            )
                        ),
                        errorCorrection = FormatQrOptions.fromOption(format)?.qrKitErrorCorrection
                            ?: FormatQrOptions.Low.qrKitErrorCorrection
                    ),
                )
            }.let {
                if (it.content.isEmpty()) null else it
            }

            update {
                copy(
                    qrCode = qr,
                    lastQrData = token,
                    currentInterval = interval
                )
            }
        }
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}