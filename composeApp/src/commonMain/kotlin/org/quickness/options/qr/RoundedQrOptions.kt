package org.quickness.options.qr

import qrgenerator.qrkitpainter.QrKitPixelShape
import qrgenerator.qrkitpainter.createCircle
import qrgenerator.qrkitpainter.createRoundCorners
import qrgenerator.qrkitpainter.createSquare

enum class RoundedQrOptions(val option: String, val qrKitShapes: QrKitPixelShape) {
    Rounded(
        option = "Rounded",
        qrKitShapes = QrKitPixelShape.createRoundCorners(.1f)
    ),
    Circular(
        option = "Circular",
        qrKitShapes = QrKitPixelShape.createCircle(.5f)
    ),
    Rectangular(
        option = "Rectangular",
        qrKitShapes = QrKitPixelShape.createSquare(.1f)
    );
    companion object {
        fun fromOption(option: String): RoundedQrOptions? =
            entries.find { it.option == option }
    }
}