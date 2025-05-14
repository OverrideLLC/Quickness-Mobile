package org.override.quickness.feature.api.navigations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import org.override.quickness.feature.home.cam.CameraRoot
import org.override.quickness.feature.home.qr.screens.QrScreen
import org.override.quickness.feature.home.shop.screen.ShopScreen
import org.override.quickness.shared.utils.deeplinks.DeepLinksHome
import org.override.quickness.shared.utils.routes.RoutesHome
import org.override.quickness.shared.utils.routes.RoutesSettings

@Composable
fun NavControllerHome(
    navController: NavHostController,
    navControllerStart: NavHostController,
    startDestination: String,
    paddingValues: PaddingValues,
    onBackClick: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        modifier = Modifier.fillMaxSize(),
        startDestination = startDestination,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    durationMillis = 100,
                    delayMillis = 0
                )
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    durationMillis = 100,
                    delayMillis = 0
                )
            )
        },
    ) {
        composable(
            route = RoutesHome.Shop.route,
            deepLinks = listOf(navDeepLink { uriPattern = DeepLinksHome.Shop.deepLink })
        ) {
            ShopScreen(paddingValues)
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
                paddingValues = paddingValues,
                navControllerHome = navControllerStart
            )
        }
        composable(
            route = RoutesHome.Camera.route,
            deepLinks = listOf(navDeepLink { uriPattern = DeepLinksHome.Camera.deepLink })
        ) {
            CameraRoot(
                navController = navController
            )
        }
    }
}