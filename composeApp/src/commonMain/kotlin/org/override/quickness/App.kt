package org.override.quickness

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.override.quickness.feature.api.navigations.NavControllerStart
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.shared.utils.routes.RoutesStart
import org.override.quickness.theme.MaterialThemeApp

@Composable
fun App(
    viewModel: AppViewModel = koinViewModel()
) {
    val isDarkTheme = viewModel.isDarkTheme.collectAsState().value
    MaterialThemeApp(
        isDarkTheme = !isDarkTheme,
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                NavControllerStart(
                    startDestination = RoutesStart.Start.route,
                    navControllerStart = rememberNavController()
                )
            }
        }
    )
}