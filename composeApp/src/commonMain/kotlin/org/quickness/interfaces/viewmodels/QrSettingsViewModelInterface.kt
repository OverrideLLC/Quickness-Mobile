package org.quickness.interfaces.viewmodels

import com.quickness.shared.utils.qr_options.ColorQrOptions
import com.quickness.shared.utils.qr_options.RoundedQrOptions

interface QrSettingsViewModelInterface {
    fun toggleFormat()
    fun toggleColor(colorQr: ColorQrOptions)
    fun toggleRounded(rounded: RoundedQrOptions)
}