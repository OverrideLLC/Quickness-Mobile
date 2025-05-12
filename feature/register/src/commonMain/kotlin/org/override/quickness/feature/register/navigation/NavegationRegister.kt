package org.override.quickness.feature.register.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.override.quickness.feature.register.components.Approbation
import org.override.quickness.feature.register.components.EmailAndPassword
import org.override.quickness.feature.register.components.InformationPersonal
import org.override.quickness.feature.register.screen.RegisterViewModel
import org.override.quickness.feature.register.states.RegisterState
import org.override.quickness.shared.ui.animations.ContentSwitchAnimation
import org.override.quickness.shared.utils.routes.RoutesRegister

/**
 * Composable function that sets up the navigation graph for the registration process.
 * It defines the navigation structure and transitions between different screens of the
 * registration flow, such as entering email and password, providing personal information,
 * and approving the registration.
 *
 * @param navController The [NavHostController] used to navigate between composable screens.
 * @param viewModel The [RegisterViewModel] containing the business logic and state for the registration process.
 * @param state The [RegisterState] representing the current state of the registration process.
 */
@Deprecated("It was replaced by WebView")
@Composable
fun NavigationRegister(
    navController: NavHostController,
    viewModel: RegisterViewModel,
    state: RegisterState,
) {
    NavHost(
        navController = navController,
        startDestination = RoutesRegister.EmailAndPassword.route,
        enterTransition = { ContentSwitchAnimation.enterTransition },
        exitTransition = { ContentSwitchAnimation.exitTransition },
    ) {
        /**
         * Composable destination for the email and password entry screen.
         * This screen allows the user to input their email and password as the first step
         * in the registration process.
         */
        composable(RoutesRegister.EmailAndPassword.route) {
            EmailAndPassword(
                viewModel = viewModel,
                state = state
            )
        }

        /**
         * Composable destination for the personal information entry screen.
         * This screen allows the user to input their personal details during registration.
         */
        composable(RoutesRegister.InformationPersonal.route) {
            InformationPersonal(
                viewModel = viewModel,
                state = state
            )
        }

        /**
         * Composable destination for the approbation screen.
         * This screen finalizes the registration process by asking for user approval
         * and may utilize the provided [uri] for additional content or actions.
         */
        composable(RoutesRegister.Approbation.route) {
            Approbation(
                viewModel = viewModel,
                state = state,
            )
        }
    }
}
