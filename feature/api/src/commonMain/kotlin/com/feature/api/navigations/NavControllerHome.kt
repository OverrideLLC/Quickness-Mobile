package com.feature.api.navigations

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.feature.home.qr.screens.QrScreen
import com.feature.home.service.screen.ServiceScreen
import com.feature.home.settings.screen.SettingsScreen
import com.feature.home.shop.screen.ShopScreen
import com.quickness.shared.utils.routes.RoutesHome
import com.shared.ui.components.animations.ContentSwitchAnimation

@Composable
fun NavControllerHome(
    navController: NavHostController,
    startDestination: String,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        modifier = Modifier.fillMaxSize(),
        startDestination = startDestination,
        enterTransition = { ContentSwitchAnimation.enterTransition },
        exitTransition = { ContentSwitchAnimation.exitTransition },
    ) {
        composable(RoutesHome.Shop.route) {
            ShopScreen(paddingValues)
        }
        composable(RoutesHome.Service.route) {
            ServiceScreen(paddingValues)
        }
        composable(RoutesHome.Qr.route) {
            QrScreen(paddingValues)
        }
        composable(RoutesHome.Settings.route) {
            SettingsScreen(
                navController = navController,
                paddingValues = paddingValues
            )
        }
    }
}