package org.quickness.ui.screens.home.settings.screens.settings_notification

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.quickness.ui.components.SettingsItem
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.email_notifications
import quickness.composeapp.generated.resources.in_app_notifications
import quickness.composeapp.generated.resources.push_notifications
import quickness.composeapp.generated.resources.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.sms_notifications
import quickness.composeapp.generated.resources.sound_and_vibration

// Notification Settings
@Composable
fun NotificationSettingsScreen() = Screen()

@Composable
fun Screen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SettingsItem(
                name = Res.string.push_notifications,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.email_notifications,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.sms_notifications,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.in_app_notifications,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.sound_and_vibration,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
        }
    }
}