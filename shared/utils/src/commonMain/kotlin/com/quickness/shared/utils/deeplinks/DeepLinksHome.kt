package com.quickness.shared.utils.deeplinks

sealed class DeepLinksHome(val deepLink: String) {
    object Shop : DeepLinksHome("https://app.quickness.override.com.mx/shop")
    object Service : DeepLinksHome("https://app.quickness.override.com.mx/service")
    object Qr : DeepLinksHome("https://app.quickness.override.com.mx/qr")
    object Settings : DeepLinksHome("https://app.quickness.override.com.mx/settings")
}