package org.quickness.ui.screens.home.settings.screens.settings_account

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.quickness.ui.components.SettingsItem
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.alternate_email_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.app_settings
import quickness.composeapp.generated.resources.arrow_forward_ios_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.change_email
import quickness.composeapp.generated.resources.change_password
import quickness.composeapp.generated.resources.change_pin
import quickness.composeapp.generated.resources.delete_account
import quickness.composeapp.generated.resources.display_settings
import quickness.composeapp.generated.resources.display_settings_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.language_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.language_settings
import quickness.composeapp.generated.resources.logout
import quickness.composeapp.generated.resources.logout_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.notification_settings
import quickness.composeapp.generated.resources.notifications_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.password_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.person_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.person_remove_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.privacy_policy
import quickness.composeapp.generated.resources.profile
import quickness.composeapp.generated.resources.security_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.security_settings
import quickness.composeapp.generated.resources.settings_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24

@Composable
fun AccountScreenSettings() = Screen()

@Composable
fun Screen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SettingsItem(
                name = Res.string.profile,
                icon = Res.drawable.person_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.change_password,
                icon = Res.drawable.password_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.change_email,
                icon = Res.drawable.alternate_email_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.delete_account,
                icon = Res.drawable.person_remove_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.logout,
                icon = Res.drawable.logout_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {}
            )
        }
    }
}