package org.quickness

class IOSPlatform : Platform {
    override val name: String = "IOS"
}

actual class Uri {
    actual fun navigate() {
        //    val safariViewController = SFSafariViewController(url: url)
//    present(safariViewController, animated: true, completion: nil)
    }
}