package org.quickness.ui.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.quickness.SharedPreference
import org.quickness.ui.animations.ContentSwitchAnimation
import org.quickness.ui.screens.home.qr.QrScreen
import org.quickness.utils.routes.RoutesHome

@Composable
fun NavigationHome(
    navController: NavHostController,
    sharedPreference: SharedPreference
) {
    NavHost(
        navController = navController,
        startDestination = RoutesHome.Qr.route,
        enterTransition = { ContentSwitchAnimation.enterTransition },
        exitTransition = { ContentSwitchAnimation.exitTransition },
    ) {
        composable(RoutesHome.Qr.route) {
            QrScreen(sharedPreference)
        }
        composable(RoutesHome.Shop.route) { }
        composable(RoutesHome.Settings.route) { }
        composable(RoutesHome.Service.route) { }
    }
}