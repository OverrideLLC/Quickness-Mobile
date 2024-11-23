package org.quickness

import androidx.compose.ui.window.ComposeUIViewController
import org.quickness.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}