package org.quickness.ui.navegation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.quickness.SharedPreference
import org.quickness.Uri
import org.quickness.ui.animations.NavAnimations
import org.quickness.ui.screens.forgot_password.ForgotPasswordScreen
import org.quickness.ui.screens.home.HomeScreen
import org.quickness.ui.screens.login.LoginScreen
import org.quickness.ui.screens.register.RegisterScreen
import org.quickness.ui.screens.start.StartScreen
import org.quickness.utils.`object`.KeysCache.UID_KEY
import org.quickness.utils.routes.RoutesStart

@Composable
fun NavigationStart() {
    val navController = rememberNavController()
    val sharedPreference = SharedPreference()
    NavHost(
        navController = navController,
        modifier = Modifier.fillMaxSize().background(colorScheme.background),
        enterTransition = { NavAnimations.enterTransition },
        exitTransition = { NavAnimations.exitTransition },
        startDestination = if (sharedPreference.getString(UID_KEY, "") == "") RoutesStart.Start.route else RoutesStart.Home.route,
    ) {
        composable(RoutesStart.Start.route) {
            StartScreen(navController)
        }
        composable(RoutesStart.Home.route) {
            HomeScreen(rememberNavController())
        }
        composable(RoutesStart.Login.route) {
            LoginScreen(navController)
        }
        composable(RoutesStart.Register.route) {
            RegisterScreen(navController)
        }
        composable(RoutesStart.ForgotPassword.route) {
            ForgotPasswordScreen()
        }
    }
}