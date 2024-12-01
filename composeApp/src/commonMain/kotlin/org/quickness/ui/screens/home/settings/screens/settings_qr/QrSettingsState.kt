package org.quickness.ui.screens.home.settings.screens.settings_qr

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.quickness.SharedPreference
import org.quickness.utils.`object`.KeysCache.FORMAT_KEY
import org.quickness.utils.`object`.KeysCache.QR_BACKGROUND_KEY
import org.quickness.utils.`object`.KeysCache.QR_COLOR_KEY
import org.quickness.utils.`object`.KeysCache.QR_TAG_KEY

data class QrSettingsState(
    private val sharedPreference: SharedPreference,
    val format: Boolean = sharedPreference.getBoolean(FORMAT_KEY, true),
    val colorTag: String = sharedPreference.getString(QR_TAG_KEY, "White"),
    val colorQr: Int = sharedPreference.getInt(QR_COLOR_KEY, Color.Black.toArgb()),
    val colorBackground: Int = sharedPreference.getInt(QR_BACKGROUND_KEY, Color.White.toArgb()),
    val readability: Boolean = false,
    val size: Boolean = false,
    val isLoadings: Boolean = false
)