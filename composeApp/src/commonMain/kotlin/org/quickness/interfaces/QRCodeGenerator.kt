package org.quickness.interfaces

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Interfaz para la generación de códigos QR.
 *
 * Esta interfaz debe ser implementada por cualquier clase que ofrezca la funcionalidad de creación de
 * códigos QR personalizados.
 */
interface QRCodeGenerator {
    /**
     * Genera un código QR con los parámetros especificados.
     *
     * @param data La información que se codificará en el código QR.
     * @param width El ancho del código QR en píxeles. Por defecto es 300.
     * @param height La altura del código QR en píxeles. Por defecto es 300.
     * @param format Si se debe aplicar un formato especial al código QR. Por defecto es `true`.
     * @param colorBackground El color de fondo del código QR, representado como un valor entero ARGB.
     * @param colorMapBits El color de los bits del mapa del código QR, representado como un valor entero ARGB.
     * @return Una imagen [ImageBitmap] que representa el código QR generado.
     */
    fun generateQRCode(
        data: String,
        width: Int = 300,
        height: Int = 300,
        format: Boolean = true,
        colorBackground: Int,
        colorMapBits: Int
    ): ImageBitmap
}