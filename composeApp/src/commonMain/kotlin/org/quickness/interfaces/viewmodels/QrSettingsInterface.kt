package org.quickness.interfaces.viewmodels

import org.quickness.options.qr.ColorQrOptions
import org.quickness.options.qr.RoundedQrOptions

interface QrSettingsInterface {
    fun toggleFormat()
    fun toggleColor(colorQr: ColorQrOptions)
    fun toggleRounded(rounded: RoundedQrOptions)
}