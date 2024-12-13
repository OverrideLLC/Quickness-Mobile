package org.quickness.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import quickness.composeapp.generated.resources.Poppins_Light
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res

@Composable
fun SettingsItem(
    name: StringResource,
    icon: DrawableResource,
    navigator: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.onBackground.copy(alpha = 0.5f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { navigator() }
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(icon),
            tint = colorScheme.tertiary,
            contentDescription = stringResource(name)
        )
        Text(
            text = stringResource(name),
            modifier = Modifier.padding(start = 16.dp),
            color = colorScheme.tertiary,
            fontFamily = FontFamily(Font(Res.font.Poppins_Medium))
        )
    }
}

@Composable
fun SettingsItemSwitch(
    name: StringResource,
    icon: DrawableResource,
    description: StringResource,
    active: Boolean,
    isActive: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.onBackground.copy(alpha = 0.5f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { isActive() }
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(icon),
                tint = if (!active) colorScheme.tertiary else colorScheme.primary,
                contentDescription = stringResource(name)
            )
            Text(
                text = stringResource(name),
                modifier = Modifier.padding(start = 16.dp),
                color = if (!active) colorScheme.tertiary else colorScheme.primary,
                fontFamily = FontFamily(Font(Res.font.Poppins_Medium))
            )
            Spacer(Modifier.weight(1f))
            Switch(
                checked = active,
                onCheckedChange = { isActive() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorScheme.primary,
                    checkedTrackColor = colorScheme.background,
                    uncheckedThumbColor = colorScheme.tertiary,
                    uncheckedTrackColor = colorScheme.background
                )
            )
        }
        Text(
            text = stringResource(description),
            color = colorScheme.tertiary,
            fontFamily = FontFamily(Font(Res.font.Poppins_Light))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSettings(
    name: StringResource,
    icon: DrawableResource,
    options: List<String>,
    exposedHeight: Dp = 300.dp,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    // Contenedor estilo `SettingsItem`
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.onBackground.copy(alpha = 0.5f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { expanded = !expanded }
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Ícono a la izquierda
        Icon(
            painter = painterResource(icon),
            tint = colorScheme.tertiary,
            contentDescription = stringResource(name)
        )
        // Nombre de la configuración
        Text(
            text = stringResource(name),
            modifier = Modifier.padding(start = 16.dp),
            color = colorScheme.tertiary,
            fontFamily = FontFamily(Font(Res.font.Poppins_Medium))
        )
        Spacer(Modifier.weight(1f))
        // Opción seleccionada y flecha de expansión
        Text(
            text = selectedOption,
            color = colorScheme.tertiary,
            fontFamily = FontFamily(Font(Res.font.Poppins_Medium))
        )
        Icon(
            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
            tint = colorScheme.tertiary,
            contentDescription = null
        )
    }

    // Menú desplegable sin FocusRequester
    if (expanded) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = exposedHeight)
                    .background(colorScheme.onBackground, RoundedCornerShape(10.dp))
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        text = {
                            Text(option, color = colorScheme.primary)
                        }
                    )
                }
            }
        }
    }
}
