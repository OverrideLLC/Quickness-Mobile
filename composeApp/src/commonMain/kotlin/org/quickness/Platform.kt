package org.quickness

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RenderEffect
import org.quickness.interfaces.QRCodeGenerator
import org.quickness.interfaces.SharedPreference

interface Platform {
    val name: String
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class Uri(url: String) : org.quickness.interfaces.Uri {
    override fun navigate()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class QRCodeGeneratorImpl() : QRCodeGenerator {
    override fun generateQRCode(
        data: String,
        width: Int,
        height: Int,
        format: Boolean,
        colorBackground: Int,
        colorMapBits: Int
    ): ImageBitmap
}

expect class RenderEffect() {
    fun createBlurEffect(radius: Float, dy: Float): RenderEffect
}

expect class GoogleMaps(){
    @Composable
    fun Map()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SharedPreference() : SharedPreference {
    override fun getString(key: String, defaultValue: String?): String
    override fun setString(key: String, value: String)
    override fun getInt(key: String, defaultValue: Int): Int
    override fun setInt(key: String, value: Int)
    override fun setList(key: String, value: List<String>)
    override fun getList(key: String): List<String>?
    override fun setMap(key: String, value: Map<String, String>)
    override fun getMap(key: String): Map<String, String>?
    override fun setLong(key: String, value: Long)
    override fun getLong(key: String, defaultValue: Long): Long
    override fun setBitmap(key: String, value: Map<String, ImageBitmap>)
    override fun getBitmap(key: String): Map<String, ImageBitmap>?
    override fun setBoolean(key: String, value: Boolean)
    override fun getBoolean(key: String, defaultValue: Boolean): Boolean
}