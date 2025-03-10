package com.feature.navControllers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.feature.start.screen.StartScreen
import com.quickness.shared.utils.routes.RoutesStart

@Composable
fun NavControllerStart(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        modifier = Modifier.fillMaxSize(),
        startDestination = startDestination,
    ) {
        composable(RoutesStart.Start.route) { StartScreen(navController) }
        composable(RoutesStart.Home.route) {  }
    }
}