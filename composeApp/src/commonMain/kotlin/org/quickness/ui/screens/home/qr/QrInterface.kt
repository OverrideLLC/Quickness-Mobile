package org.quickness.ui.screens.home.qr

import androidx.compose.ui.graphics.ImageBitmap

interface QrInterface {
    fun generateQRCode(): ImageBitmap?
}