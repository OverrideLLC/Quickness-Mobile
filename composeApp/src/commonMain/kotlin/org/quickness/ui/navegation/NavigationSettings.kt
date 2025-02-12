package org.quickness.ui.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.quickness.ui.animations.NavAnimations
import org.quickness.ui.screens.home.settings.SettingsScreen
import org.quickness.ui.screens.home.settings.screens.settings_account.AccountScreenSettings
import org.quickness.ui.screens.home.settings.screens.settings_app.AppSettingsScreen
import org.quickness.ui.screens.home.settings.screens.settings_display.DisplaySettingsScreen
import org.quickness.ui.screens.home.settings.screens.settings_language.LanguageSettingsScreen
import org.quickness.ui.screens.home.settings.screens.settings_notification.NotificationSettingsScreen
import org.quickness.ui.screens.home.settings.screens.settings_privacy.PrivacySettingsScreen
import org.quickness.ui.screens.home.settings.screens.settings_qr.QrScreenSettings
import org.quickness.ui.screens.home.settings.screens.settings_security.SecuritySettingsScreen
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
        composable(RoutesSettings.AccountSettings.route) { AccountScreenSettings() }
        composable(RoutesSettings.PrivacySettings.route) { PrivacySettingsScreen() }
        composable(RoutesSettings.NotificationSettings.route) { NotificationSettingsScreen() }
        composable(RoutesSettings.DisplaySettings.route) { DisplaySettingsScreen() }
        composable(RoutesSettings.LanguageSettings.route) { LanguageSettingsScreen() }
        composable(RoutesSettings.SecuritySettings.route) { SecuritySettingsScreen() }
        composable(RoutesSettings.AppSettings.route) { AppSettingsScreen() }
        composable(RoutesSettings.HelpCenter.route) {  }
        composable(RoutesSettings.AboutUs.route) {  }
    }
}