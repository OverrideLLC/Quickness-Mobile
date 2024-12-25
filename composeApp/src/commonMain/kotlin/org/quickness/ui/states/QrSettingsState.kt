package org.quickness.ui.states

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.quickness.SharedPreference
import org.quickness.utils.`object`.KeysCache

/**
 * Represents the state of the QR code settings.
 *
 * This data class holds the current settings for generating QR codes,
 * including format, colors, rounded corners, readability, size, and loading status.
 * It retrieves the initial values from SharedPreferences using the provided [sharedPreference] instance.
 *
 * @property sharedPreference The SharedPreference instance used to retrieve and store settings.
 * @property format Indicates whether the QR code should include format information (default: true).
 * @property colorTag The color of the tag text in the QR code (default: "White").
 * @property colorQr The color of the QR code itself (default: White).
 * @property colorBackground The background color of the QR code (default: Black).
 * @property rounded The type of rounded corners for the QR code (default: "circular").
 * @property readability A boolean indicating if readability enhancements are enabled (default: false).
 * @property size A boolean indicating if size adjustments are enabled (default: false).
 * @property isLoadings A boolean indicating if the settings are currently being loaded (default: false).
 */
data class QrSettingsState(
    private val sharedPreference: SharedPreference,
    val format: Boolean = sharedPreference.getBoolean(KeysCache.FORMAT_KEY, true),
    val colorTag: String = sharedPreference.getString(KeysCache.QR_TAG_KEY, "White"),
    val colorQr: Int = sharedPreference.getInt(KeysCache.QR_COLOR_KEY, Color.Companion.White.toArgb()),
    val colorBackground: Int = sharedPreference.getInt(KeysCache.QR_BACKGROUND_KEY, Color.Companion.Black.toArgb()),
    val rounded: String = sharedPreference.getString(KeysCache.ROUNDED_ROUNDED_QR_KEY, "circular"),
    val readability: Boolean = false,
    val size: Boolean = false,
    val isLoadings: Boolean = false
)