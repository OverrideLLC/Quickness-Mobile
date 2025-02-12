package org.quickness.interfaces.viewmodels

import org.quickness.utils.options.qr.ColorQrOptions
import org.quickness.utils.options.qr.RoundedQrOptions

interface QrSettingsInterface {
    fun toggleFormat()
    fun toggleColor(colorQr: ColorQrOptions)
    fun toggleRounded(rounded: RoundedQrOptions)
}