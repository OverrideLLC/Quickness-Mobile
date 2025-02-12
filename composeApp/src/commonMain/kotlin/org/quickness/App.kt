package org.quickness

import androidx.compose.runtime.Composable
import org.quickness.tooling.Preview
import org.quickness.ui.navegation.NavigationStart
import org.quickness.ui.theme.MaterialThemeApp

@Composable
@Preview
fun App() {
    MaterialThemeApp {
        NavigationStart()
    }
}