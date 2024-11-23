package org.quickness

import androidx.compose.runtime.Composable
import io.ktor.client.HttpClient
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.quickness.ui.navegation.NavigationStart
import org.quickness.ui.theme.MaterialThemeApp

@Composable
@Preview
fun App() {
    MaterialThemeApp {
        //Navegación de la aplicación
        NavigationStart()
    }
}