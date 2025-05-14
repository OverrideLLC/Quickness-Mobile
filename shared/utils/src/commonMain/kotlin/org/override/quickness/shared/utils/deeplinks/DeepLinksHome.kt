package org.override.quickness.shared.utils.deeplinks

import org.override.quickness.shared.utils.objects.Constants.BASE_URL

sealed class DeepLinksHome(val deepLink: String) {
    object Shop : DeepLinksHome("${BASE_URL}/shop")
    object Service : DeepLinksHome("${BASE_URL}/service")
    object Qr : DeepLinksHome("${BASE_URL}/qr")
    object Settings : DeepLinksHome("${BASE_URL}/settings")
    object Camera : DeepLinksHome("${BASE_URL}/camera")
}