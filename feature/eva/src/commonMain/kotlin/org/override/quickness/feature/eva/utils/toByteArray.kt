package org.override.quickness.feature.eva.utils
import androidx.compose.ui.graphics.ImageBitmap

/**
 * Convierte un ImageBitmap a un ByteArray.
 *
 * @param format El formato de imagen deseado (ej. "PNG", "JPEG").
 * @param quality La calidad de compresión para formatos como JPEG (0-100). Ignorado para PNG.
 * @return ByteArray con los datos de la imagen, o null si la conversión falla.
 */
expect fun ImageBitmap.toByteArray(format: String = "PNG", quality: Int = 100): ByteArray?