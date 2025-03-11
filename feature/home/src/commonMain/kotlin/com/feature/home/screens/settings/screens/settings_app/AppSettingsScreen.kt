package com.feature.home.screens.settings.screens.settings_app

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.quickness.ui.components.component.SettingsItem
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.about_us
import quickness.composeapp.generated.resources.contact_support
import quickness.composeapp.generated.resources.feedback
import quickness.composeapp.generated.resources.help_center
import quickness.composeapp.generated.resources.legal
import quickness.composeapp.generated.resources.open_source_licenses
import quickness.composeapp.generated.resources.rate_us
import quickness.composeapp.generated.resources.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.terms_of_service
import quickness.composeapp.generated.resources.version

// App Settings
@Composable
fun AppSettingsScreen() = Screen()

@Composable
fun Screen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SettingsItem(
                name = Res.string.version,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.about_us,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.feedback,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.rate_us,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.help_center,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.contact_support,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.legal,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.terms_of_service,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.open_source_licenses,
                icon = Res.drawable.settings_24dp_E8EAED_FILL1_wght400_GRAD0_opsz24,
                navigator = {}
            )
        }
    }
}