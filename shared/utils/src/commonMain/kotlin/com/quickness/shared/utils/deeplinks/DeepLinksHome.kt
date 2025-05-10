package com.quickness.shared.utils.deeplinks

import com.quickness.shared.utils.objects.Constants.BASE_URL

sealed class DeepLinksHome(val deepLink: String) {
    object Shop : DeepLinksHome("${BASE_URL}/shop")
    object Service : DeepLinksHome("${BASE_URL}/service")
    object Qr : DeepLinksHome("${BASE_URL}/qr")
    object Settings : DeepLinksHome("${BASE_URL}/settings")
}