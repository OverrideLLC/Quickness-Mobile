package com.feature.api.navigations

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.feature.home.qr.screens.QrScreen
import com.feature.home.service.screen.ServiceScreen
import com.feature.home.shop.screen.ShopScreen
import com.quickness.shared.utils.deeplinks.DeepLinksHome
import com.quickness.shared.utils.deeplinks.DeepLinksStart
import com.quickness.shared.utils.routes.RoutesHome
import com.quickness.shared.utils.routes.RoutesSettings
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
        composable(
            route = RoutesHome.Shop.route,
            deepLinks = listOf(navDeepLink { uriPattern = DeepLinksHome.Shop.deepLink })
        ) {
            ShopScreen(paddingValues)
        }
        composable(
            route = RoutesHome.Service.route,
            deepLinks = listOf(navDeepLink { uriPattern = DeepLinksHome.Service.deepLink })
        ) {
            ServiceScreen(paddingValues)
        }
        composable(
            route = RoutesHome.Qr.route,
            deepLinks = listOf(navDeepLink { uriPattern = DeepLinksHome.Qr.deepLink })
        ) {
            QrScreen(paddingValues)
        }
        composable(
            route = RoutesHome.Settings.route,
            deepLinks = listOf(navDeepLink { uriPattern = DeepLinksHome.Settings.deepLink })
        ) {
            NavControllerSettings(
                navController = rememberNavController(),
                startDestination = RoutesSettings.Settings.route,
                paddingValues = paddingValues
            )
        }
    }
}