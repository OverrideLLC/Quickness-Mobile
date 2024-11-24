package org.quickness

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build

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