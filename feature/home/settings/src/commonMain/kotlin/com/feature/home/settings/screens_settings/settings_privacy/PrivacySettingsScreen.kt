package com.feature.home.settings.screens_settings.settings_privacy

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PrivacySettingsScreen() = Screen()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(viewModel: PrivacySettingsViewModel = koinViewModel()) {
}
