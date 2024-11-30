package org.quickness.ui.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.quickness.SharedPreference
import org.quickness.ui.animations.NavAnimations
import org.quickness.ui.screens.home.settings.SettingsScreen
import org.quickness.ui.screens.home.settings.screens.settings_qr.QrScreenSettings
import org.quickness.utils.routes.RoutesSettings

@Composable
fun NavigationSettings() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RoutesSettings.Settings.route,
        enterTransition = { NavAnimations.enterTransition },
        exitTransition = { NavAnimations.exitTransition }
    ) {
        composable(RoutesSettings.Settings.route) { SettingsScreen(navController) }
        composable(RoutesSettings.SettingsQr.route) { QrScreenSettings() }
        composable(RoutesSettings.AccountSettings.route) { }
        composable(RoutesSettings.PrivacySettings.route) { }
        composable(RoutesSettings.NotificationSettings.route) { }
        composable(RoutesSettings.DisplaySettings.route) { }
        composable(RoutesSettings.LanguageSettings.route) { }
        composable(RoutesSettings.SecuritySettings.route) { }
        composable(RoutesSettings.AppSettings.route) { }
        composable(RoutesSettings.HelpCenter.route) { }
        composable(RoutesSettings.AboutUs.route) { }
    }
}