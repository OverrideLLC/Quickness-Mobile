package com.quickness.shared.utils.deeplinks

sealed class DeepLinksStart(val deepLink: String) {
    object Start : DeepLinksStart("https://app.quickness.override.com.mx/start")
    object Home : DeepLinksStart("https://app.quickness.override.com.mx/home")
    object Camera : DeepLinksStart("https://app.quickness.override.com.mx/camera")
}