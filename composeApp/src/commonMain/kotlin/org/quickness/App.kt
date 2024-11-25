package org.quickness

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.quickness.data.remote.FirebaseService
import org.quickness.ui.navegation.NavigationStart
import org.quickness.ui.theme.MaterialThemeApp

@Composable
@Preview
fun App(
    uri: Uri,

) {
    MaterialThemeApp {
        //Navegación de la aplicación
        NavigationStart(uri)
    }
}