package com.quickness.shared.utils.deeplinks

import com.quickness.shared.utils.objects.Constants.BASE_URL

sealed class DeepLinksStart(val deepLink: String) {
    object Start : DeepLinksStart("${BASE_URL}/start")
    object Home : DeepLinksStart("${BASE_URL}/home")
    object Camera : DeepLinksStart("${BASE_URL}/camera")
}