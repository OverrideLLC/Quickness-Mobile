package org.override.quickness.shared.utils.deeplinks

import org.override.quickness.shared.utils.objects.Constants.BASE_URL

sealed class DeepLinksStart(val deepLink: String) {
    object Start : DeepLinksStart("${BASE_URL}/start")
    object Home : DeepLinksStart("${BASE_URL}/home/{uid}")
    object Camera : DeepLinksStart("${BASE_URL}/camera")
    object Eva : DeepLinksStart("${BASE_URL}/eva")
}