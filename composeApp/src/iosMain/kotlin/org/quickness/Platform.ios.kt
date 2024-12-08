package org.quickness

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import org.quickness.interfaces.QRCodeGenerator
import org.quickness.interfaces.Uri

actual class GoogleMaps actual constructor(){
    @Composable
    actual fun Map() {
        TODO("Not yet implemented")
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Uri actual constructor(url: String) : Uri {
    actual override fun navigate() {
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SharedPreference actual constructor() : SharedPreference {
    actual override fun getString(key: String, defaultValue: String?): String {
        TODO("Not yet implemented")
    }

    actual override fun setString(key: String, value: String) {
    }

    actual override fun getInt(key: String, defaultValue: Int): Int {
        TODO("Not yet implemented")
    }

    actual override fun setInt(key: String, value: Int) {
    }

    actual override fun setList(key: String, value: List<String>) {
    }

    actual override fun getList(key: String): List<String>? {
        TODO("Not yet implemented")
    }

    actual override fun setMap(
        key: String,
        value: Map<String, String>
    ) {
    }

    actual override fun getMap(key: String): Map<String, String>? {
        TODO("Not yet implemented")
    }

    actual override fun setLong(key: String, value: Long) {
    }

    actual override fun getLong(key: String, defaultValue: Long): Long {
        TODO("Not yet implemented")
    }

    actual override fun setBoolean(key: String, value: Boolean) {
    }

    actual override fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean {
        TODO("Not yet implemented")
    }
}