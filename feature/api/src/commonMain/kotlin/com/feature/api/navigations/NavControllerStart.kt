package com.feature.api.navigations

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.feature.home.screen.HomeScreen
import com.feature.login.screen.LoginScreen
import com.feature.start.screen.StartScreen
import com.quickness.shared.utils.routes.RoutesHome
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
        composable(RoutesStart.Start.route) {
            StartScreen(
                navController = navController,
                contentAuth = { LoginScreen() }
            )
        }
        composable(RoutesStart.Home.route) {
            val navController = rememberNavController()
            HomeScreen(navController = navController) {
                NavControllerHome(navController, RoutesHome.Qr.route)
            }
        }
    }
}