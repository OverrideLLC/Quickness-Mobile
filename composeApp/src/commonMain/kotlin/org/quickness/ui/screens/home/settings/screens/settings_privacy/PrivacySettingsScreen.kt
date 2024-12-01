package org.quickness.ui.screens.home.settings.screens.settings_privacy

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.quickness.ui.components.SettingsItem
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.data_deletion_request
import quickness.composeapp.generated.resources.data_download
import quickness.composeapp.generated.resources.download_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.manage_data
import quickness.composeapp.generated.resources.monitoring_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.scan_delete_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24

@Composable
fun PrivacySettingsScreen() = Screen()

@Composable
fun Screen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SettingsItem(
                name = Res.string.manage_data,
                icon = Res.drawable.monitoring_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.data_download,
                icon = Res.drawable.download_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {}
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.data_deletion_request,
                icon = Res.drawable.scan_delete_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {}
            )
        }
    }
}