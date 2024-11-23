package org.quickness.ui.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.quickness.ui.animations.ContentSwitchAnimation
import org.quickness.ui.screens.register.Approbation
import org.quickness.ui.screens.register.EmailAndPassword
import org.quickness.ui.screens.register.InformationPersonal
import org.quickness.ui.screens.register.RegisterViewModel
import org.quickness.utils.RoutesRegister

@Composable
fun NavigationRegister(navController: NavHostController, viewModel: RegisterViewModel) {
    NavHost(
        navController = navController,
        startDestination = RoutesRegister.EmailAndPassword.route,
        enterTransition = { ContentSwitchAnimation.enterTransition },
        exitTransition = { ContentSwitchAnimation.exitTransition },
    ) {
        composable(RoutesRegister.EmailAndPassword.route) {
            EmailAndPassword(viewModel)
        }
        composable(RoutesRegister.InformationPersonal.route) {
            InformationPersonal(viewModel)
        }
        composable(RoutesRegister.Approbation.route) {
            Approbation(viewModel)
        }
    }
}