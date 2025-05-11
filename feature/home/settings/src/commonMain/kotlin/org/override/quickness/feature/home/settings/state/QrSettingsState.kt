package org.override.quickness.feature.home.settings.state

import org.override.quickness.shared.utils.qr_options.ColorQrOptions
import org.override.quickness.shared.utils.qr_options.RoundedQrOptions

data class QrSettingsState(
    val format: String? = null,
    val colorQr: String = ColorQrOptions.Black.option,
    val rounded: String = RoundedQrOptions.Rounded.option,
)