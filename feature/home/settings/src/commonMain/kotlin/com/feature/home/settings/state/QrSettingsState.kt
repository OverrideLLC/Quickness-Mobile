package com.feature.home.settings.state

import com.quickness.shared.utils.qr_options.ColorQrOptions
import com.quickness.shared.utils.qr_options.FormatQrOptions
import com.quickness.shared.utils.qr_options.RoundedQrOptions

data class QrSettingsState(
    val format: String = FormatQrOptions.Low.option,
    val colorQr: String = ColorQrOptions.Black.option,
    val rounded: String = RoundedQrOptions.Rounded.option,
)