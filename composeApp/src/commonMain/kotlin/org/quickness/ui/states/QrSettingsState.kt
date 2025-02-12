package org.quickness.ui.states

import org.quickness.utils.options.qr.ColorQrOptions
import org.quickness.utils.options.qr.FormatQrOptions
import org.quickness.utils.options.qr.RoundedQrOptions

data class QrSettingsState(
    val format: String = FormatQrOptions.Low.option,
    val colorQr: String = ColorQrOptions.Black.option,
    val rounded: String = RoundedQrOptions.Rounded.option,
)