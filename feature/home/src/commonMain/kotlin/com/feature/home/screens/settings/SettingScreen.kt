package com.feature.home.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.asStateFlow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.quickness.ui.components.component.SettingsItem

@Composable
fun SettingsScreen(navController: NavController) = Screen(navController)

@OptIn(KoinExperimentalAPI::class)
@Composable
private fun Screen(navController: NavController, viewModel: SettingsViewModel = koinViewModel()) {
    val states = viewModel.settingsEnum.asStateFlow().value
    LazyColumn(
        content = {
            states.getAllSettings().forEach { setting ->
                item {
                    SettingsItem(
                        name = setting.titleRes,
                        icon = setting.iconRes,
                        navigator = { navController.navigate(setting.route) }
                    )
                    Spacer(Modifier.padding(10.dp))
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}