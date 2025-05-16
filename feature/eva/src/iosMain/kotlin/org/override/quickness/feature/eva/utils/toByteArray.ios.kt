package org.override.quickness.feature.eva.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap // Puede requerir una dependencia específica o estar disponible
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image

/**
 * Implementación real para plataformas basadas en Skia (iOS, Desktop)
 * para convertir un ImageBitmap a un ByteArray.
 */
actual fun ImageBitmap.toByteArray(format: String, quality: Int): ByteArray? {
    val skiaBitmap = this.asSkiaBitmap() // Convierte el ImageBitmap de Compose a un Skia Bitmap
    val skiaImage = Image.makeFromBitmap(skiaBitmap)

    val encodedFormat = when (format.uppercase()) {
        "PNG" -> EncodedImageFormat.PNG
        "JPEG", "JPG" -> EncodedImageFormat.JPEG
        "WEBP" -> EncodedImageFormat.WEBP
        // Añade otros formatos si es necesario
        else -> EncodedImageFormat.PNG
    }

    return try {
        skiaImage.encodeToData(encodedFormat, quality)?.bytes
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}