package org.quickness

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RenderEffect.createBlurEffect
import android.graphics.Shader
import android.net.Uri
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.asImageBitmap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.quickness.di.ContextProvider
import org.quickness.interfaces.QRCodeGenerator
import org.quickness.interfaces.SharedPreference
import org.quickness.utils.`object`.KeysCache
import org.quickness.utils.`object`.KeysCache.TOKENS_BITMAP_KEY
import java.io.ByteArrayOutputStream

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
actual class QRCodeGeneratorImpl actual constructor() : QRCodeGenerator {
    actual override fun generateQRCode(
        data: String,
        width: Int,
        height: Int,
        format: Boolean,
        colorBackground: Int,
        colorMapBits: Int
    ): ImageBitmap {
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(
            data,
            BarcodeFormat.QR_CODE,
            width,
            height,
            mapOf(EncodeHintType.ERROR_CORRECTION to if (format) ErrorCorrectionLevel.L else ErrorCorrectionLevel.H)
        )
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(
                    x,
                    y,
                    if (bitMatrix[x, y]) colorMapBits else colorBackground
                )
            }
        }
        return bitmap.asImageBitmap()
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

    @RequiresApi(Build.VERSION_CODES.O)
    actual override fun setBitmap(key: String, value: Map<String, ImageBitmap>) {
        val bitmapMap = value.mapValues { (_, bitmap) ->
            val byteArray = imageBitmapToByteArray(bitmap)
            Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
        val serializedMap = Json.encodeToString(bitmapMap)
        sharedPreferences.edit().putString(key, serializedMap).apply()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    actual override fun getBitmap(key: String): Map<String, ImageBitmap>? {
        val mapString = sharedPreferences.getString(TOKENS_BITMAP_KEY, null)
        return if (mapString != null) {
            // Deserializar el JSON a un mapa de cadenas (Base64)
            val base64Map: Map<String, String> = Json.decodeFromString(mapString)
            // Convertir cada cadena Base64 de vuelta a ByteArray y luego a ImageBitmap
            base64Map.mapValues { (_, base64String) ->
                val byteArray = Base64.decode(base64String, Base64.DEFAULT) // Decodificar Base64
                byteArrayToImageBitmap(byteArray)
            }
        } else {
            null
        }
    }

    private fun imageBitmapToByteArray(imageBitmap: ImageBitmap): ByteArray {
        val bitmap = imageBitmap.asAndroidBitmap() // En Android
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream) // Comprimir a PNG
        return outputStream.toByteArray()
    }

    private fun byteArrayToImageBitmap(byteArray: ByteArray): ImageBitmap {
        val bitmap =
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size) // Decodificar ByteArray
        return bitmap.asImageBitmap()
    }

    actual override fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    actual override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
}

actual class RenderEffect actual constructor() {
    @RequiresApi(Build.VERSION_CODES.S)
    actual fun createBlurEffect(
        radius: Float,
        dy: Float
    ): RenderEffect {
        return createBlurEffect(
            16f,
            16f,
            Shader.TileMode.CLAMP
        ).asComposeRenderEffect()
    }
}

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