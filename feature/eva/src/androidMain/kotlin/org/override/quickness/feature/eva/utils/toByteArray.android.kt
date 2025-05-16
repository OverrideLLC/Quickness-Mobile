package org.override.quickness.feature.eva.utils

import androidx.compose.ui.graphics.ImageBitmap
import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream

/**
 * Implementación real para Android para convertir un ImageBitmap a un ByteArray.
 */
actual fun ImageBitmap.toByteArray(format: String, quality: Int): ByteArray? {
    val androidBitmap = this.asAndroidBitmap()
    val stream = ByteArrayOutputStream()
    val compressFormat = when (format.uppercase()) {
        "PNG" -> Bitmap.CompressFormat.PNG
        "JPEG", "JPG" -> Bitmap.CompressFormat.JPEG
        "WEBP" -> Bitmap.CompressFormat.WEBP_LOSSY
        else -> Bitmap.CompressFormat.PNG
    }

    return try {
        androidBitmap.compress(compressFormat, quality, stream)
        stream.toByteArray()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        try {
            stream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}