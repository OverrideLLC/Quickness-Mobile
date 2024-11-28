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
actual class QRCodeGeneratorImpl actual constructor() : QRCodeGenerator {
    override fun generateQRCode(data: String, width: Int, height: Int): ImageBitmap {
        TODO("Not yet implemented")
    }
}

actual class SharedPreference actual constructor() : SharedPreference