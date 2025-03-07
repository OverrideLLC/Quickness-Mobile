package org.quickness.ui.navegation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.quickness.shared.utils.routes.RoutesHome
import org.quickness.ui.animations.ContentSwitchAnimation
import org.quickness.ui.screens.home.qr.QrScreen
import org.quickness.ui.screens.home.service.ServiceScreen
import org.quickness.ui.screens.home.shop.ShopScreen

@Composable
fun NavigationHome(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = RoutesHome.Qr.route,
        modifier = Modifier.padding(paddingValues),
        enterTransition = { ContentSwitchAnimation.enterTransition },
        exitTransition = { ContentSwitchAnimation.exitTransition },
    ) {
        composable(RoutesHome.Qr.route) { QrScreen() }
        composable(RoutesHome.Shop.route) { ShopScreen() }
        composable(RoutesHome.Settings.route) { NavigationSettings() }
        composable(RoutesHome.Service.route) { ServiceScreen() }
    }
}