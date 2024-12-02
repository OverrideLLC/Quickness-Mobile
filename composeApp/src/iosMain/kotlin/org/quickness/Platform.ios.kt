package org.quickness

import androidx.compose.ui.graphics.ImageBitmap
import org.quickness.interfaces.QRCodeGenerator

class IOSPlatform : Platform {
    override val name: String = "IOS"
}

actual class Uri {
    actual fun navigate() {
        //    val safariViewController = SFSafariViewController(url: url)
//    present(safariViewController, animated: true, completion: nil)
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class QRCodeGeneratorImpl actual constructor(sharedPreference: SharedPreference) : QRCodeGenerator {
    actual override fun generateQRCode(data: String, width: Int, height: Int): ImageBitmap {
        TODO("Not yet implemented")
    }
}

actual class SharedPreference actual constructor() : SharedPreference
actual class EncryptPasswordPBKDF2 actual constructor(password: String) {
    actual fun encrypt(): String {
        TODO("Not yet implemented")
    }
}

actual class RenderEffect actual constructor() {
    actual fun createBlurEffect(
        radius: Float,
        dy: Float
    ): RenderEffect {
        TODO("Not yet implemented")
    }
}

actual class GoogleMaps actual constructor()