package org.quickness.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    inversePrimary = inversePrimary,
    secondary = secondary,
    onSecondary = onSecondary,
    secondaryContainer = secondaryContainer,
    onSecondaryContainer = onSecondaryContainer,
    tertiary = tertiary,
    onTertiary = onTertiary,
    tertiaryContainer = tertiaryContainer,
    onTertiaryContainer = onTertiaryContainer,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface,
    surfaceVariant = surfaceVariant,
    onSurfaceVariant = onSurfaceVariant,
    surfaceTint = surfaceTint,
    inverseSurface = inverseSurface,
    inverseOnSurface = inverseOnSurface,
    error = error,
    onError = onError,
    errorContainer = errorContainer,
    onErrorContainer = onErrorContainer,
    outline = outline,
    outlineVariant = outlineVariant,
    scrim = scrim,
    surfaceBright = surfaceBright,
    surfaceContainer = surfaceContainer,
    surfaceContainerHigh = surfaceContainerHigh,
    surfaceContainerHighest = surfaceContainerHighest,
    surfaceContainerLow = surfaceContainerLow,
    surfaceContainerLowest = surfaceContainerLowest,
    surfaceDim = surfaceDim
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFFb3eaf5), // Fondo claro para el contenedor primario
    onPrimaryContainer = Color(0xFF003f43), // Contraste oscuro sobre el contenedor primario
    inversePrimary = Color(0xFF004f53), // Tono inverso de Primary

    secondary = secondary,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFc6e5e7), // Fondo claro para el contenedor secundario
    onSecondaryContainer = Color(0xFF004d51), // Contraste oscuro sobre el contenedor secundario

    tertiary = tertiary,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFFd9d9d9), // Fondo claro para el contenedor terciario
    onTertiaryContainer = Color(0xFF2a2a2a), // Contraste oscuro sobre el contenedor terciario

    background = Color.White, // Fondo blanco para el modo claro
    onBackground = Color.Black, // Texto oscuro sobre fondo claro

    surface = Color(0xFFf5f5f5), // Superficie clara
    onSurface = Color.Black, // Texto oscuro sobre la superficie clara

    surfaceVariant = Color(0xFFe0e0e0), // Variante más clara de la superficie
    onSurfaceVariant = Color.Black, // Texto oscuro para la variante de superficie

    surfaceTint = primary, // Tint relacionado con Primary
    inverseSurface = Color(0xFF000000), // Fondo oscuro para inversión
    inverseOnSurface = Color(0xFFffffff), // Texto claro sobre fondo inverso

    error = error,
    onError = Color.Black,
    errorContainer = Color(0xFFffd6d6), // Fondo claro para contenedor de error
    onErrorContainer = Color(0xFF93000a), // Contraste oscuro sobre contenedor de error

    outline = Color(0xFFbdbdbd), // Tono intermedio para bordes
    outlineVariant = Color(0xFF757575), // Variante más oscura del borde
    scrim = Color(0x40000000), // Fondo translúcido
    surfaceBright = Color(0xFFe0e0e0), // Superficie más clara
    surfaceContainer = Color(0xFFffffff), // Contenedor claro
    surfaceContainerHigh = Color(0xFFf0f0f0), // Contenedor más claro
    surfaceContainerHighest = Color(0xFFdcdcdc), // Contenedor más claro aún
    surfaceContainerLow = Color(0xFFf5f5f5), // Contenedor más tenue
    surfaceContainerLowest = Color(0xFFe6e6e6), // Contenedor más tenue
    surfaceDim = Color(0xFFc4c4c4) // Superficie atenuada
)

@Composable
fun MaterialThemeApp(content: @Composable () -> Unit) {
    val themeViewModel = ThemeViewModel()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    // Cambia el colorScheme basado en el valor de isDarkTheme
    MaterialTheme(
        colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme,
        content = content
    )
}

class ThemeViewModel : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    // Función para alternar el tema
    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
}
