package org.quickness.utils.options.qr

import qrgenerator.qrkitpainter.QrKitErrorCorrection

enum class FormatQrOptions(
    val option: String,
    val qrKitErrorCorrection: QrKitErrorCorrection
) {
    High(
        option = "High",
        qrKitErrorCorrection = QrKitErrorCorrection.High
    ),
    Low(
        option = "Low",
        qrKitErrorCorrection = QrKitErrorCorrection.Low
    );

    companion object {
        fun fromOption(option: String): FormatQrOptions? {
            return entries.find { it.option == option }
        }
    }
}