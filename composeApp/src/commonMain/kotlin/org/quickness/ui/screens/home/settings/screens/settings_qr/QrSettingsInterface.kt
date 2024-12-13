package org.quickness.ui.screens.home.settings.screens.settings_qr

import androidx.compose.ui.graphics.ImageBitmap

interface QrSettingsInterface {
    fun toggleFormat()
    fun toggleColor(colorQr: Int, colorBackground: Int, colorTag: String)
    fun toggleRounded(rounded: String)
}