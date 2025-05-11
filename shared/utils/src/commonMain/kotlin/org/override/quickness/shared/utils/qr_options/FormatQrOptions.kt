<<<<<<<< HEAD:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/utils/qr_options/FormatQrOptions.kt
package org.override.quickness.shared.utils.qr_options
========
package org.quickness.options.qr
>>>>>>>> origin/master:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/options/qr/FormatQrOptions.kt

import qrgenerator.qrkitpainter.QrKitErrorCorrection

enum class FormatQrOptions(
    val option: String,
    val qrKitErrorCorrection: QrKitErrorCorrection
) {
    High(
        option = "High",
        qrKitErrorCorrection = QrKitErrorCorrection.High
    ),
    Low(
        option = "Low",
        qrKitErrorCorrection = QrKitErrorCorrection.Low
    );

    companion object {
        fun fromOption(option: String): FormatQrOptions? {
            return entries.find { it.option == option }
        }
    }
}