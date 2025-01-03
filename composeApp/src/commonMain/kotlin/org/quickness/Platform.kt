@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.quickness

import androidx.compose.runtime.Composable
import org.quickness.interfaces.plataform.SharedPreference
import org.quickness.interfaces.plataform.Uri

expect class Uri(url: String) : Uri {
    override fun navigate()
}

expect class GoogleMaps() {
    @Composable
    fun Map()
}

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
    override fun setBoolean(key: String, value: Boolean)
    override fun getBoolean(key: String, defaultValue: Boolean): Boolean
    override fun setFloat(key: String, value: Float)
    override fun getFloat(key: String, defaultValue: Float): Float
    override fun logOut()
}