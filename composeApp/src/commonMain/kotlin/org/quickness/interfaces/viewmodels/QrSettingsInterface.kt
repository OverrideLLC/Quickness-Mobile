package org.quickness.interfaces.viewmodels

interface QrSettingsInterface {
    fun toggleFormat()
    fun toggleColor(colorQr: Int, colorBackground: Int, colorTag: String)
    fun toggleRounded(rounded: String)
}