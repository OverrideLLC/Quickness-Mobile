package org.quickness.ui.screens.home.qr

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.quickness.SharedPreference
import org.quickness.interfaces.viewmodels.QrInterface
import org.quickness.ui.states.QrState
import org.quickness.utils.`object`.KeysCache.FORMAT_KEY
import org.quickness.utils.`object`.KeysCache.QR_COLOR_KEY
import org.quickness.utils.`object`.KeysCache.ROUNDED_ROUNDED_QR_KEY
import org.quickness.utils.`object`.KeysCache.TOKENS_KEY
import qrgenerator.qrkitpainter.QrKitBallShape
import qrgenerator.qrkitpainter.QrKitBrush
import qrgenerator.qrkitpainter.QrKitColors
import qrgenerator.qrkitpainter.QrKitErrorCorrection
import qrgenerator.qrkitpainter.QrKitOptions
import qrgenerator.qrkitpainter.QrKitPixelShape
import qrgenerator.qrkitpainter.QrKitShapes
import qrgenerator.qrkitpainter.QrPainter
import qrgenerator.qrkitpainter.createCircle
import qrgenerator.qrkitpainter.createRoundCorners
import qrgenerator.qrkitpainter.createSquare
import qrgenerator.qrkitpainter.solidBrush

class QrViewModel(
    private val sharedPreference: SharedPreference,
) : ViewModel(), QrInterface {

    private val _qrState = MutableStateFlow(QrState(sharedPreference))
    val qrState = _qrState

    private val viewModelScopeJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelScopeJob)

    init {
        viewModelScope.launch { monitorQrUpdates() }
    }

    override fun monitorQrUpdates() {
        viewModelScope.launch {
            while (true) {
                val currentInterval = calculateCurrentInterval()
                val token = getCurrentToken()
                if (_qrState.value.currentInterval != currentInterval || token != _qrState.value.lastQrData) {
                    updateQrCodeForToken(token, currentInterval)
                }
                delay(10 * 60 * 1000L)
            }
        }
    }

    private fun calculateCurrentInterval(): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val startMinute = (now.minute / 10) * 10
        val endMinute = startMinute + 10

        val startHour = now.hour
        val endHour = if (endMinute >= 60) (startHour + 1) % 24 else startHour

        val adjustedStartMinute = startMinute % 60
        val adjustedEndMinute = endMinute % 60

        return "${startHour.toString().padStart(2, '0')}:${
            adjustedStartMinute.toString().padStart(2, '0')
        }-${endHour.toString().padStart(2, '0')}:${adjustedEndMinute.toString().padStart(2, '0')}"
    }

    private fun getCurrentToken(): String {
        val tokensMap = sharedPreference.getMap(TOKENS_KEY) ?: emptyMap()
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val currentTokenIndex =
            (now.hour * 6) + (now.minute / 10)
        return tokensMap[currentTokenIndex.toString()] ?: "default_token"
    }

    override fun updateQrCodeForToken(token: String, interval: String) {
        println("Generating QR for token: $token at interval $interval")

        if (token == _qrState.value.lastQrData && interval == _qrState.value.currentInterval) return

        viewModelScope.launch {
            val qr = withContext(Dispatchers.Default) {
                QrPainter(
                    content = token,
                    config = QrKitOptions(
                        shapes = QrKitShapes(
                            darkPixelShape = when (
                                sharedPreference.getString(
                                    ROUNDED_ROUNDED_QR_KEY,
                                    "Rounded"
                                )
                            ) {
                                "Rounded" -> QrKitPixelShape.createRoundCorners(.5f)
                                "Circular" -> QrKitPixelShape.createCircle(1f)
                                "Rectangular" -> QrKitPixelShape.createSquare(1f)
                                else -> QrKitPixelShape.createRoundCorners(.5f)
                            },
                            ballShape = QrKitBallShape.createRoundCorners(.1f),
                        ),
                        colors = QrKitColors(
                            lightBrush = QrKitBrush.solidBrush(color = Color.Transparent),
                            ballBrush = QrKitBrush.solidBrush(
                                color = Color(
                                    sharedPreference.getInt(
                                        QR_COLOR_KEY,
                                        Color.White.toArgb()
                                    )
                                )
                            ),
                            frameBrush = QrKitBrush.solidBrush(
                                color = Color(
                                    sharedPreference.getInt(
                                        QR_COLOR_KEY,
                                        Color.White.toArgb()
                                    )
                                )
                            ),
                            darkBrush = QrKitBrush.solidBrush(
                                color = Color(
                                    sharedPreference.getInt(
                                        QR_COLOR_KEY,
                                        Color.White.toArgb()
                                    )
                                )
                            )
                        ),
                        errorCorrection = if (
                            sharedPreference.getBoolean(
                                FORMAT_KEY,
                                true
                            )
                        ) QrKitErrorCorrection.Low else QrKitErrorCorrection.High
                    )
                )
            }

            _qrState.value = _qrState.value.copy(
                qrCode = qr,
                lastQrData = token,
                currentInterval = interval
            )
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScopeJob.cancel()
    }
}