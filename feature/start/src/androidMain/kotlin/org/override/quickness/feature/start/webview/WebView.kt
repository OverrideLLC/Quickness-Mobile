package org.override.quickness.feature.start.webview

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.override.quickness.shared.utils.ContextProvider

actual class WebView actual constructor() {
    @Composable
    actual fun View(url: String) {
        val context = ContextProvider.getContext()
        if (context != null) {
            WebViewScreen(
                url = url,
                context = context,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            println("Context is null")
        }
    }

    @Composable
    fun WebViewScreen(url: String, modifier: Modifier = Modifier, context: Context) {
        AndroidView(
            modifier = modifier,
            factory = {
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient() // Para que los enlaces se abran dentro del WebView
                    settings.javaScriptEnabled = true // Habilitar JavaScript si es necesario
                    loadUrl(url)
                }
            },
            update = { webView -> }
        )
    }
}