package org.quickness.plataform

import android.content.Intent
import androidx.core.net.toUri
import org.quickness.utils.ContextProvider

actual class Uri actual constructor(url: String) : org.quickness.interfaces.plataform.Uri {
    private val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    actual override fun navigate() {
        ContextProvider.getContext()!!.startActivity(intent)
    }
}