package org.quickness

import androidx.compose.ui.graphics.ImageBitmap
import org.quickness.interfaces.QRCodeGenerator

interface Platform {
    val name: String
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class Uri {
    fun navigate()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class QRCodeGeneratorImpl(): QRCodeGenerator{
    override fun generateQRCode(data: String, width: Int, height: Int): ImageBitmap
}