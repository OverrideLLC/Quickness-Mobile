package com.feature.home.screen.settings.screens.settings_security

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.quickness.ui.components.component.SettingsItem
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.biometric_authentication
import quickness.composeapp.generated.resources.change_pin
import quickness.composeapp.generated.resources.enable_2fa
import quickness.composeapp.generated.resources.session_management
import quickness.composeapp.generated.resources.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24

// Security Settings
@Composable
fun SecuritySettingsScreen() = Screen()

@Composable
fun Screen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SettingsItem(
                name = Res.string.enable_2fa,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.change_pin,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.biometric_authentication,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.session_management,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
        }
    }
}