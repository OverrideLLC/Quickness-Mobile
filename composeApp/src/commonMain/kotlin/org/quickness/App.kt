package org.quickness

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.quickness.ui.navegation.NavigationStart
import org.quickness.ui.theme.MaterialThemeApp

@Composable
fun App() {
    MaterialThemeApp(
        isDarkTheme = false,
        content = {
            Surface(modifier = Modifier.fillMaxSize()) {
                NavigationStart()
            }
        }
    )
}