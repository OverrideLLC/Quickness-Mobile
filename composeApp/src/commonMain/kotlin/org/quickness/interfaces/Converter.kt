package org.quickness.interfaces

import androidx.compose.ui.graphics.ImageBitmap

interface Converter {
    fun imageBitmapToByteArray(imageBitmap: ImageBitmap): ByteArray
    fun byteArrayToImageBitmap(byteArray: ByteArray): ImageBitmap
}