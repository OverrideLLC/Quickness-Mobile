package com.feature.home.settings.screens_settings.settings_display

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.feature.home.settings.components.SettingsItemSwitch
import com.shared.resources.drawable.ResourceNameKey
import com.shared.resources.strings.Strings
import com.shared.ui.components.styles.ShimmerListVertical
import org.koin.compose.viewmodel.koinViewModel

// Display Settings
@Composable
fun DisplaySettingsScreen(
    paddingValues: PaddingValues
) = Screen(paddingValues = paddingValues)

@Composable
fun Screen(
    paddingValues: PaddingValues,
    viewModel: DisplaySettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val loading by viewModel.loading.collectAsState()
    ShimmerListVertical(
        count = 1,
        modifier = Modifier.padding(paddingValues),
        isLoading = loading,
        content = { Content(paddingValues = paddingValues, state = state, viewModel = viewModel) }
    )
}

@Composable
fun Content(
    paddingValues: PaddingValues,
    state: DisplaySettingsViewModel.DisplaySettingsState,
    viewModel: DisplaySettingsViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            SettingsItemSwitch(
                name = Strings.DisplaySettings.THEME,
                icon = viewModel.getDrawable(ResourceNameKey.PALETTE_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
                description = Strings.DisplaySettings.THEME_DESCRIPTION,
                active = state.isDarkTheme,
                isActive = { viewModel.toggleDarkTheme() }
            )
        }
    }
}