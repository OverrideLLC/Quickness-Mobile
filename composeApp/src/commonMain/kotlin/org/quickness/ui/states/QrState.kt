package org.quickness.ui.states

import org.quickness.utils.options.qr.ColorQrOptions
import qrgenerator.qrkitpainter.QrPainter

/**
 * Represents the state of the QR code generation and display.
 *
 * This data class holds information about the QR code, including its visual representation,
 * the last data encoded, the current time interval (if applicable), and the color of the QR code.
 *
 * @property qrCode The QrPainter object responsible for drawing the QR code.
 * @property lastQrData The last data that was encoded into the QR code.
 * @property currentInterval The current time interval used for generating time-based QR codes (optional).
 */
data class QrState(
    var qrCode: QrPainter? = null,
    var lastQrData: String? = null,
    var currentInterval: String? = null,
    val colors: List<Int> = ColorQrOptions.Black.colors,
    var isVisible: Boolean = false,
    var isExpanded: Boolean = false,
    var isBlurred: Boolean = true,
    var showBiometric: Boolean = false
)
