package org.override.quickness.wasm

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        Qr()
    }
}

@Composable
fun Qr() {
    Image(
        painter = rememberQrCodePainter("https://conditions.override.com.mx/quickness"),
        contentDescription = "QrCode",
        modifier = Modifier.fillMaxSize(.5f)
    )
}