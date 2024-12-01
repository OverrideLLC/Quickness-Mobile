package org.quickness.interfaces

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap

interface QRCodeGenerator {
    fun generateQRCode(
        data: String,
        width: Int = 300,
        height: Int = 300,
        format: Boolean = true,
        colorBackground: Int,
        colorMapBits: Int
    ): ImageBitmap
}