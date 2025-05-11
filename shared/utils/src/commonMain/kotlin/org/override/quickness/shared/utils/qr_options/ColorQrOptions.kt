<<<<<<<< HEAD:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/utils/qr_options/ColorQrOptions.kt
package org.override.quickness.shared.utils.qr_options

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
========
package org.quickness.options.qr

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.quickness.options.qr.ColorQrOptions.entries

>>>>>>>> origin/master:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/options/qr/ColorQrOptions.kt

enum class ColorQrOptions(val option: String, val colors: List<Int>) {
    Blue(
        option = "Blue",
        colors = listOf(Color(0xFF5ce1e6).toArgb(), Color(0xFF000000).toArgb())
    ),
    Black(
        option = "Black",
        colors = listOf(Color.Black.toArgb(), Color.White.toArgb())
    ),
    White(
        option = "White",
        colors = listOf(Color.White.toArgb(), Color.Black.toArgb())
    ),
    Cream(
        option = "Cream",
        colors = listOf(Color(0xff2B3A67).toArgb(), Color(0xFFFAF3E0).toArgb())
    ),
    Warm(
        option = "Warm",
        colors = listOf(Color(0xff3E2723).toArgb(), Color(0xffFFF8E1).toArgb())
    );

    companion object {
        fun fromOption(option: String): ColorQrOptions? {
            return entries.find { it.option == option }
        }
    }
}