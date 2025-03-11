package com.feature.home.screens.settings.screens.settings_display

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.quickness.ui.components.component.SettingsItem
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.dark_mode
import quickness.composeapp.generated.resources.font_size
import quickness.composeapp.generated.resources.light_mode
import quickness.composeapp.generated.resources.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.system_default
import quickness.composeapp.generated.resources.theme

// Display Settings
@Composable
fun DisplaySettingsScreen() = Screen()

@Composable
fun Screen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SettingsItem(
                name = Res.string.dark_mode,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.light_mode,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.system_default,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.font_size,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.theme,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
        }
    }
}