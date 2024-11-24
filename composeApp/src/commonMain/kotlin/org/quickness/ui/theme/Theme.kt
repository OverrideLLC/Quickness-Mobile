package org.quickness.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = Background,
    error = Error,
    onBackground = OnBackground,
    onSecondary = OnSecondary,
    tertiary = tertiary,
)

@Composable
fun MaterialThemeApp(content: @Composable () -> Unit) =
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content,
    )