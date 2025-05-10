package org.override.quickness.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import quickness.composeapp.generated.resources.Poppins_Bold
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Poppins_Thin
import quickness.composeapp.generated.resources.Res

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
    onTertiary = primary,
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
    primaryContainer = onPrimaryContainer,
    onPrimary = primary,
    secondary = onSecondary,
    onSecondary = secondary,
    tertiary = onPrimary,
    onTertiary = Color(0xff15a9b2),
    background = Color.White,
    onBackground = Color.White,
    surface = onSurface,
    onSurface = surface,
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
fun MaterialThemeApp(content: @Composable () -> Unit, isDarkTheme: Boolean) {
    MaterialTheme(
        colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme,
        typography = TypographyApp(),
        content = content
    )
}

@Composable
fun TypographyApp(): Typography = Typography(
    titleLarge = TextStyle(fontFamily = FontFamily(Font(Res.font.Poppins_Bold))),
    titleMedium = TextStyle(fontFamily = FontFamily(Font(Res.font.Poppins_Medium))),
    titleSmall = TextStyle(fontFamily = FontFamily(Font(Res.font.Poppins_Thin))),
    bodyLarge = TextStyle(fontFamily = FontFamily(Font(Res.font.Poppins_Bold))),
    bodyMedium = TextStyle(fontFamily = FontFamily(Font(Res.font.Poppins_Medium))),
    bodySmall = TextStyle(fontFamily = FontFamily(Font(Res.font.Poppins_Thin))),
    labelLarge = TextStyle(fontFamily = FontFamily(Font(Res.font.Poppins_Bold))),
    labelMedium = TextStyle(fontFamily = FontFamily(Font(Res.font.Poppins_Medium))),
    labelSmall = TextStyle(fontFamily = FontFamily(Font(Res.font.Poppins_Thin))),
)
