package org.override.quickness.feature.start.webview

import androidx.compose.runtime.Composable

expect class WebView(){
    @Composable
    fun View(url: String)
}