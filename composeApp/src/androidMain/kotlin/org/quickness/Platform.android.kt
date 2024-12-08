package org.quickness

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.quickness.di.ContextProvider
import org.quickness.interfaces.SharedPreference
import org.quickness.utils.`object`.KeysCache

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Uri actual constructor(url: String) : org.quickness.interfaces.Uri {
    private val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    actual override fun navigate() {
        ContextProvider.getContext()!!.startActivity(intent)
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SharedPreference actual constructor() : SharedPreference {
    private val sharedPreferences =
        ContextProvider.getContext()!!.getSharedPreferences(KeysCache.MY_PREFS, MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }  // Configuración de JSON

    actual override fun getString(key: String, defaultValue: String?): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue ?: ""
    }

    actual override fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    actual override fun getInt(key: String, defaultValue: Int): Int {
        if (!sharedPreferences.contains(key)) {
            sharedPreferences.edit().putInt(key, defaultValue).apply()
        }
        return sharedPreferences.getInt(key, defaultValue)
    }

    actual override fun setInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    actual override fun setList(key: String, value: List<String>) {
        sharedPreferences.edit().putStringSet(key, value.toSet()).apply()
    }

    actual override fun getList(key: String): List<String>? {
        return sharedPreferences.getStringSet(key, emptySet())?.toList() ?: listOf(" ")
    }

    // Función para guardar un mapa (Map<String, String>)
    actual override fun setMap(key: String, value: Map<String, String>) {
        val jsonString = json.encodeToString(value)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    // Función para obtener un mapa (Map<String, String>)
    actual override fun getMap(key: String): Map<String, String>? {
        val mapString = sharedPreferences.getString(key, null)
        return if (mapString != null) {
            // Convertir la cadena JSON de vuelta al mapa usando Kotlin Serialization
            json.decodeFromString<Map<String, String>>(mapString)
        } else {
            null
        }
    }

    actual override fun setLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    actual override fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    actual override fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    actual override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class GoogleMaps actual constructor() {
    @Composable
    actual fun Map() {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(19.475426, -102.072805),
                15f
            )
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = true,
                isTrafficEnabled = true,
                isIndoorEnabled = true,
                isBuildingEnabled = true
            ),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = LatLng(19.475426, -102.072805)),
                title = "TecNm"
            )
        }
    }
}
