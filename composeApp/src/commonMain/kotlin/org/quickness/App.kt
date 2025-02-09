package org.quickness

import androidx.compose.runtime.Composable
import org.quickness.ui.navegation.NavigationStart
import org.quickness.ui.theme.MaterialThemeApp

@Composable
fun App() {
    MaterialThemeApp {
        NavigationStart()
    }
}