package org.quickness.interfaces

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.flow.StateFlow

interface SharedPreference {
    fun getString(key: String, defaultValue: String?): String
    fun setString(key: String, value: String)
    fun getInt(key: String, defaultValue: Int): Int
    fun setInt(key: String, value: Int)
    fun setList(key: String, value: List<String>)
    fun getList(key: String): List<String>?
    fun getMap(key: String): Map<String, String>?
    fun setMap(key: String, value: Map<String, String>)
    fun setLong(key: String, value: Long)
    fun getLong(key: String, defaultValue: Long): Long
    fun setBitmap(key: String, value: Map<String, ImageBitmap>)
    fun getBitmap(key: String): Map<String, ImageBitmap>?
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
}