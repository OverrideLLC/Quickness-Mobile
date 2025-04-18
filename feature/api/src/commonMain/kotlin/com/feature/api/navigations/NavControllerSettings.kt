package com.feature.api.navigations

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.feature.home.settings.screen.SettingsScreen
import com.feature.home.settings.screens_settings.settings_qr.QrScreenSettings
import com.quickness.shared.utils.routes.RoutesSettings
import com.shared.ui.components.animations.ContentSwitchAnimation

@Composable
fun NavControllerSettings(
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
        composable(RoutesSettings.SettingsQr.route) {
            QrScreenSettings(paddingValues)
        }
        composable(RoutesSettings.Settings.route) {
            SettingsScreen(navController = navController, paddingValues = paddingValues)
        }
        composable(RoutesSettings.AccountSettings.route) {}
        composable(RoutesSettings.PrivacySettings.route) {}
        composable(RoutesSettings.NotificationSettings.route) {}
        composable(RoutesSettings.DisplaySettings.route) {}
        composable(RoutesSettings.LanguageSettings.route) {}
        composable(RoutesSettings.SecuritySettings.route) {}
        composable(RoutesSettings.AppSettings.route) {}
        composable(RoutesSettings.HelpCenter.route) {}
        composable(RoutesSettings.AboutUs.route) {}
    }
}