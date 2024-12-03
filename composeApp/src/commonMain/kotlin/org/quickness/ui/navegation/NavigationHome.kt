package org.quickness.ui.navegation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.quickness.ui.animations.ContentSwitchAnimation
import org.quickness.ui.screens.home.qr.QrScreen
import org.quickness.ui.screens.home.service.ServiceScreen
import org.quickness.ui.screens.home.shop.ShopScreen
import org.quickness.utils.routes.RoutesHome

@Composable
fun NavigationHome(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = RoutesHome.Qr.route,
        modifier = Modifier
            .padding(paddingValues)
            .background(Color.Transparent),
        enterTransition = { ContentSwitchAnimation.enterTransition },
        exitTransition = { ContentSwitchAnimation.exitTransition },
    ) {
        composable(RoutesHome.Qr.route) { QrScreen() }
        composable(RoutesHome.Shop.route) { ShopScreen() }
        composable(RoutesHome.Settings.route) { NavigationSettings() }
        composable(RoutesHome.Service.route) { ServiceScreen() }
    }
}