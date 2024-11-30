package org.quickness.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
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
            .background(color = colorScheme.onBackground, shape = RoundedCornerShape(10.dp))
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
            .background(color = colorScheme.onBackground, shape = RoundedCornerShape(10.dp))
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
                    checkedThumbColor = colorScheme.secondary,
                    checkedTrackColor = colorScheme.primary,
                    uncheckedThumbColor = colorScheme.primary,
                    uncheckedTrackColor = colorScheme.secondary
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