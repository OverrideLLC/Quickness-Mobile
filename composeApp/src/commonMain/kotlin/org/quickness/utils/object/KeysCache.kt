package org.quickness.utils.`object`

import androidx.datastore.preferences.core.stringPreferencesKey

object KeysCache {
    const val MY_PREFS = "MyPrefs"
    const val LAST_REQUEST_KEY = "last_request_date"
    const val TOKENS_KEY = "tokens_key"
    val UID_KEY = stringPreferencesKey("uid_key")
    const val ROUNDED_ROUNDED_QR_KEY = "rounded_rounded_corners_qr_key"
    const val FORMAT_KEY = "format_key"
    const val QR_COLOR_KEY = "qr_color_key"
    const val QR_BACKGROUND_KEY = "qr_size_key"
    const val QR_TAG_KEY = "qr_tag_key"
    const val MIN_REQUEST_HOUR = 0
    val JWT_KEY = stringPreferencesKey("jwt_key")
    val JWT_FIREBASE_KEY = stringPreferencesKey("firebase_jwt_key")
}