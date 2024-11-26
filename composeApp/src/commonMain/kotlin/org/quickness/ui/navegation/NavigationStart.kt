package org.quickness.ui.navegation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.quickness.Uri
import org.quickness.ui.animations.NavAnimations
import org.quickness.ui.screens.home.HomeScreen
import org.quickness.ui.screens.login.LoginScreen
import org.quickness.ui.screens.register.RegisterScreen
import org.quickness.ui.screens.start.StartScreen
import org.quickness.utils.routes.RoutesStart

@Composable
fun NavigationStart(uri: Uri) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        modifier = Modifier.fillMaxSize().background(colorScheme.background),
        enterTransition = { NavAnimations.enterTransition },
        exitTransition = { NavAnimations.exitTransition },
        startDestination = RoutesStart.Home.route
    ) {
        composable(RoutesStart.Start.route) {
            StartScreen(navController)
        }
        composable(RoutesStart.Home.route) {
            HomeScreen()
        }
        composable(RoutesStart.Login.route) {
            LoginScreen(navController)
        }
        composable(RoutesStart.Register.route) {
            RegisterScreen(navController, uri)
        }
        composable(RoutesStart.ForgotPassword.route) {
            //ForgotPasswordScreen(navController)
        }
    }
}