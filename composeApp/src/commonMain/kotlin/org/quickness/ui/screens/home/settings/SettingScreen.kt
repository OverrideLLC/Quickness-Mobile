package org.quickness.ui.screens.home.settings

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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.koin.compose.viewmodel.koinViewModel
import org.quickness.ui.components.SettingsItem

@Composable
fun SettingsScreen(navController: NavController) = Screen(navController)

@Composable
private fun Screen(navController: NavController, viewModel: SettingsViewModel = koinViewModel()) {
    val states = viewModel.settingsState.asStateFlow().value
    LazyColumn(
        content = {
            states.settingsMap.forEach { (_, values) ->
                val name = values[0] as StringResource
                val icon = values[1] as DrawableResource
                val route = values[2] as String

                item {
                    SettingsItem(
                        name = name,
                        icon = icon,
                        navigator = { navController.navigate(route) }
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