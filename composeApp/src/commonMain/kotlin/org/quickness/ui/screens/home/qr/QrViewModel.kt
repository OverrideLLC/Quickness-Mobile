package org.quickness.ui.screens.home.qr

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.quickness.SharedPreference
import org.quickness.interfaces.repository.DataStoreRepository
import org.quickness.interfaces.repository.TokenDatabaseRepository
import org.quickness.interfaces.viewmodels.QrInterface
import org.quickness.ui.states.QrState
import org.quickness.utils.`object`.KeysCache.FORMAT_KEY
import org.quickness.utils.`object`.KeysCache.QR_COLOR_KEY
import org.quickness.utils.`object`.KeysCache.ROUNDED_ROUNDED_QR_KEY
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
    private val tokenDatabaseRepository: TokenDatabaseRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val sharedPreference: SharedPreference
) : ViewModel(), QrInterface {

    private val _qrState = MutableStateFlow(QrState())
    val qrState = _qrState.asStateFlow()

    override fun updateQrCodeForToken(token: String?, interval: String) {
        if (token == _qrState.value.lastQrData && interval == _qrState.value.currentInterval) return

        viewModelScope.launch {
            val qr = withContext(Dispatchers.Default) {
                QrPainter(
                    content = token ?: "",
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
}