package org.override.quickness

import androidx.compose.ui.window.ComposeUIViewController
import org.override.quickness.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}