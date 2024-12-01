package org.quickness.ui.screens.home.settings.screens.settings_qr

import androidx.compose.ui.graphics.ImageBitmap

interface QrSettingsInterface {
    fun toggleFormat()
    fun generatePlaceholderBitmap(): ImageBitmap
    fun toggleColor(colorQr: Int, colorBackground: Int, colorTag: String)
    fun restartTokens(onSuccessfulRestart: (Boolean) -> Unit, onFailedRestart: () -> Unit)
}