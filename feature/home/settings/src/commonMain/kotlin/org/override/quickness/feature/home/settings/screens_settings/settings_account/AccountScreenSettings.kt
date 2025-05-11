package org.override.quickness.feature.home.settings.screens_settings.settings_account

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AccountScreenSettings() = Screen()

@Preview
@Composable
fun AccountScreenSettingsPreview() = Screen()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    viewModel: AccountSettingsViewModel = koinViewModel(),
) {
}

