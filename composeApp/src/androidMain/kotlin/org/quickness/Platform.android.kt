package org.quickness

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.quickness.interfaces.QRCodeGenerator
import org.quickness.interfaces.SharedPreference
import org.quickness.utils.`object`.KeysCache
import org.quickness.utils.`object`.KeysCache.TOKENS_BITMAP_KEY
import java.io.ByteArrayOutputStream

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Uri(private val context: Context, url: String) {
    private val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    actual fun navigate() {
        context.startActivity(intent)
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class QRCodeGeneratorImpl actual constructor() : QRCodeGenerator {
    actual override fun generateQRCode(data: String, width: Int, height: Int): ImageBitmap {
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(
            data,
            BarcodeFormat.QR_CODE,
            width,
            height,
        )
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(
                    x,
                    y,
                    if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
                )
            }
        }
        return bitmap.asImageBitmap()
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SharedPreference(contextActual: Context?) : SharedPreference {
    private val sharedPreferences =
        contextActual!!.getSharedPreferences(KeysCache.MY_PREFS, MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }  // Configuración de JSON

    actual override fun getString(key: String, defaultValue: String?): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue ?: ""
    }

    actual override fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    actual override fun getInt(key: String, defaultValue: Int): Int {
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
        // Convertir el mapa a JSON usando Kotlin Serialization
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

    actual override fun setBitmap(key: String, value: Map<String, ImageBitmap>) {
        // Convertir cada ImageBitmap a ByteArray y luego a Base64
        val bitmapMap = value.mapValues { (_, bitmap) ->
            val byteArray = imageBitmapToByteArray(bitmap)
            Base64.encodeToString(byteArray, Base64.DEFAULT) // Codificar a Base64
        }
        // Serializar el mapa a JSON
        val serializedMap = Json.encodeToString(bitmapMap)
        // Guardar el JSON en SharedPreferences
        sharedPreferences.edit().putString(key, serializedMap).apply()
    }

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
}