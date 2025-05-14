package org.override.quickness.feature.home.settings.components

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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun SettingsItem(
    name: String,
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
            .padding(15.dp)
            .heightIn(
                min = 20.dp,
                max = 30.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(icon),
            tint = colorScheme.tertiary,
            contentDescription = name
        )
        Text(
            text = name,
            modifier = Modifier.padding(start = 16.dp),
            color = colorScheme.tertiary,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
        )
    }
}

@Composable
fun SettingsItemSwitch(
    name: String,
    icon: DrawableResource,
    description: String,
    active: Boolean,
    modifier: Modifier = Modifier,
    isActive: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.onBackground.copy(alpha = 0.5f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { isActive() }
            .padding(15.dp)
            .heightIn(
                min = 20.dp,
                max = 30.dp
            ),
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
                contentDescription = name
            )
            Text(
                text = name,
                modifier = Modifier.padding(start = 16.dp),
                color = if (!active) colorScheme.tertiary else colorScheme.primary,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
            )
            Spacer(Modifier.weight(1f))
            Switch(
                checked = active,
                onCheckedChange = { isActive() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorScheme.primary,
                    checkedTrackColor = colorScheme.onBackground,
                    uncheckedThumbColor = colorScheme.tertiary,
                    uncheckedTrackColor = colorScheme.background
                )
            )
        }
        Text(
            text = description,
            color = colorScheme.tertiary,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSettings(
    name: String,
    icon: DrawableResource,
    options: List<String>,
    exposedHeight: Dp = 300.dp,
    selectedOption: String,
    iconKey: DrawableResource,
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
            .padding(15.dp)
            .heightIn(
                min = 20.dp,
                max = 30.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Ícono a la izquierda
        Icon(
            painter = painterResource(icon),
            tint = colorScheme.tertiary,
            contentDescription = name
        )
        // Nombre de la configuración
        Text(
            text = name,
            modifier = Modifier.padding(start = 16.dp),
            color = colorScheme.tertiary,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
        )
        Spacer(Modifier.weight(1f))
        // Opción seleccionada y flecha de expansión
        Text(
            text = selectedOption,
            color = colorScheme.tertiary,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
        )
    }

    // Menú desplegable sin FocusRequester
    if (expanded) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = exposedHeight)
                    .background(
                        colorScheme.onBackground.copy(alpha = 0.5f),
                        RoundedCornerShape(10.dp)
                    )
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        text = {
                            Text(option, color = colorScheme.tertiary)
                        }
                    )
                }
            }
        }
    }
}
