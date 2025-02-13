package org.quickness.ui.navegation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.feature.screens.start.StartScreen
import org.jetbrains.compose.resources.Font
import org.koin.compose.viewmodel.koinViewModel
import org.quickness.ui.animations.NavAnimations
import org.quickness.ui.screens.forgot_password.ForgotPasswordScreen
import org.quickness.ui.screens.home.HomeScreen
import org.quickness.ui.screens.login.LoginScreen
import org.quickness.ui.screens.register.RegisterScreen
import org.quickness.utils.routes.RoutesStart
import quickness.composeapp.generated.resources.LogoQuicknessQC
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.app_name
import quickness.composeapp.generated.resources.login
import quickness.composeapp.generated.resources.logout_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.register

@Composable
fun NavigationStart(
    viewModel: SharedNavigationViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val sharedNavigationState by remember { viewModel.sharedNavigationState }.collectAsState()
    val startDestination = if (sharedNavigationState.uid.isEmpty()) {
        RoutesStart.Start.route
    } else {
        RoutesStart.Home.route
    }
    NavHost(
        navController = navController,
        modifier = Modifier.fillMaxSize().background(colorScheme.background),
        enterTransition = { NavAnimations.enterTransition },
        exitTransition = { NavAnimations.exitTransition },
        startDestination = startDestination
    ) {
        composable(RoutesStart.Start.route) {
            StartScreen(
                navController = navController,
                title = Res.string.app_name,
                recurseButton1 = Res.string.login to Res.drawable.logout_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                recurseButton2 = Res.string.register to Res.drawable.logout_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                fontFamily = FontFamily(Font(Res.font.Poppins_Medium)),
                icon = Res.drawable.LogoQuicknessQC
            )
        }
        composable(RoutesStart.Home.route) { HomeScreen(rememberNavController()) }
        composable(RoutesStart.Login.route) { LoginScreen(navController) }
        composable(RoutesStart.Register.route) { RegisterScreen(navController) }
        composable(RoutesStart.ForgotPassword.route) { ForgotPasswordScreen() }
    }
}