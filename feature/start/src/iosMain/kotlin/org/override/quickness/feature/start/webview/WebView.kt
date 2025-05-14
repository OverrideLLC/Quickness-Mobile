package org.override.quickness.feature.start.webview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class WebView actual constructor() {
    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual fun View(url: String) {
        UIKitView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                val webViewConfiguration = WKWebViewConfiguration()
                val webView = WKWebView(
                    frame = CGRectMake(0.0, 0.0, 0.0, 0.0),
                    configuration = webViewConfiguration
                )
                val nsUrl = NSURL.URLWithString(url)
                if (nsUrl != null) {
                    val request = NSURLRequest.requestWithURL(nsUrl)
                    webView.loadRequest(request)
                }
                webView
            },
        )
    }
}