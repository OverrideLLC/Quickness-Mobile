package org.quickness

import androidx.compose.runtime.Composable
import org.quickness.interfaces.plataform.SharedPreference
import org.quickness.interfaces.plataform.Uri

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class GoogleMaps actual constructor() {
    @Composable
    actual fun Map() {
        TODO("Not yet implemented")
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Uri actual constructor(url: String) : Uri {
    actual override fun navigate() {
        TODO("Not yet implemented")
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SharedPreference actual constructor() : SharedPreference {
    override actual fun getString(key: String, defaultValue: String?): String {
        TODO("Not yet implemented")
    }

    override actual fun setString(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override actual fun getInt(key: String, defaultValue: Int): Int {
        TODO("Not yet implemented")
    }

    override actual fun setInt(key: String, value: Int) {
        TODO("Not yet implemented")
    }

    override actual fun setList(key: String, value: List<String>) {
        TODO("Not yet implemented")
    }

    override actual fun getList(key: String): List<String>? {
        TODO("Not yet implemented")
    }

    override actual fun setMap(key: String, value: Map<String, String>) {
        TODO("Not yet implemented")
    }

    override actual fun getMap(key: String): Map<String, String>? {
        TODO("Not yet implemented")
    }

    override actual fun setLong(key: String, value: Long) {
        TODO("Not yet implemented")
    }

    override actual fun getLong(key: String, defaultValue: Long): Long {
        TODO("Not yet implemented")
    }

    override actual fun setBoolean(key: String, value: Boolean) {
        TODO("Not yet implemented")
    }

    override actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override actual fun setFloat(key: String, value: Float) {
        TODO("Not yet implemented")
    }

    override actual fun getFloat(key: String, defaultValue: Float): Float {
        TODO("Not yet implemented")
    }

    override actual fun logOut() {
        TODO("Not yet implemented")
    }
}