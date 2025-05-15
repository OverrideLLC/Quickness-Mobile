package org.override.quickness.feature.api.navigations

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.override.quickness.feature.home.service.lyra.LyraRoot
import org.override.quickness.feature.home.service.screen.WidgetsRoot
import org.override.quickness.shared.utils.routes.RoutesWidget

@Composable
fun NavControllerWidgets(
    paddingValues: PaddingValues
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        startDestination = RoutesWidget.Widgets.route
    ) {
        composable(RoutesWidget.Widgets.route) {
            WidgetsRoot(navController = navController)
        }
        composable(RoutesWidget.Lyra.route) {
            LyraRoot(
                paddingValues =  paddingValues,
            )
        }
    }
}