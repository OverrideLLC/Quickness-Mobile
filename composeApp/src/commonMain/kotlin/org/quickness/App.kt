package org.quickness

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.quickness.ui.navegation.NavigationStart
import org.quickness.ui.theme.MaterialThemeApp

@Composable
@Preview
fun App(
    uri: Uri,
    sharedPreference: SharedPreference
) {
    MaterialThemeApp {
        NavigationStart(uri, sharedPreference)
    }
}