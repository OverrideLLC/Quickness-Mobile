package org.quickness.ui.screens.home.qr

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.quickness.interfaces.repository.DataStoreRepository
import org.quickness.interfaces.repository.TokenDatabaseRepository
import org.quickness.interfaces.viewmodels.QrInterface
import org.quickness.options.qr.ColorQrOptions
import org.quickness.options.qr.FormatQrOptions
import org.quickness.options.qr.QrOptionsKeys.FORMAT_KEY
import org.quickness.options.qr.QrOptionsKeys.QR_COLOR_KEY
import org.quickness.options.qr.QrOptionsKeys.ROUNDED_QR_KEY
import org.quickness.options.qr.RoundedQrOptions
import org.quickness.ui.states.QrState
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
) : ViewModel(), QrInterface {
    private val _qrState = MutableStateFlow(QrState())
    val qrState = _qrState.asStateFlow()

    override fun updateQrCodeForToken(token: String?, interval: String) {
        if (token == _qrState.value.lastQrData && interval == _qrState.value.currentInterval) return
        viewModelScope.launch {
            val format = dataStoreRepository.getString(
                key = FORMAT_KEY,
                defaultValue = FormatQrOptions.Low.option
            ) ?: FormatQrOptions.Low.option

            val colors = dataStoreRepository.getString(
                key = QR_COLOR_KEY,
                defaultValue = ColorQrOptions.Black.option
            ).let { colorOption ->
                ColorQrOptions.fromOption(colorOption ?: ColorQrOptions.Black.option)?.colors
                    ?: ColorQrOptions.Black.colors
            }

            val qrKitShapes = dataStoreRepository.getString(
                key = ROUNDED_QR_KEY,
                defaultValue = RoundedQrOptions.Rounded.option
            ) ?: RoundedQrOptions.Rounded.option
            val qr = withContext(Dispatchers.Default) {
                QrPainter(
                    content = token ?: "",
                    config = QrKitOptions(
                        shapes = QrKitShapes(
                            darkPixelShape = RoundedQrOptions.fromOption(qrKitShapes)?.qrKitShapes
                                ?: RoundedQrOptions.Rounded.qrKitShapes,
                            ballShape = QrKitBallShape.createRoundCorners(.1f)
                        ),
                        colors = QrKitColors(
                            lightBrush = QrKitBrush.solidBrush(color = Color.Transparent),
                            ballBrush = QrKitBrush.solidBrush(
                                color = Color(colors[0])
                            ),
                            frameBrush = QrKitBrush.solidBrush(
                                color = Color(colors[1])
                            ),
                            darkBrush = QrKitBrush.solidBrush(
                                color = Color(colors[1])
                            )
                        ),
                        errorCorrection = FormatQrOptions.fromOption(format)?.qrKitErrorCorrection
                            ?: FormatQrOptions.Low.qrKitErrorCorrection
                    ),
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