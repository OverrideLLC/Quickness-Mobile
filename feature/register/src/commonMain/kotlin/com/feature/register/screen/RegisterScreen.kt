package com.feature.register.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.feature.register.navigation.NavigationRegister
import com.quickness.shared.utils.routes.RoutesRegister
import com.quickness.shared.utils.routes.RoutesStart
import com.shared.resources.drawable.ResourceNameKey
import com.shared.resources.strings.Strings
import com.shared.ui.components.helpers.Message
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * Main composable for the registration screen.
 *
 * @param navController Main navigation controller.
 */
@Composable
fun RegisterScreen(navController: NavController) = RegisterContent(navController)

/**
 * Registration screen that contains the header, content, and bottom bar.
 *
 * @param navController Main navigation controller.
 * @param viewModel ViewModel to manage the state and actions of the screen.
 */
@OptIn(KoinExperimentalAPI::class)
@Composable
fun RegisterContent(
    navController: NavController,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val navControllerRegister = rememberNavController()
    val currentBackStackEntry by navControllerRegister.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val state = viewModel.state.collectAsState().value

    Scaffold(
        modifier = Modifier.padding(vertical = 50.dp),
        topBar = {
            RegisterHeader(
                currentRoute = currentRoute ?: RoutesRegister.EmailAndPassword.route,
                navControllerRegister = navControllerRegister,
                navController = navController,
                viewModel = viewModel
            )
        },
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                NavigationRegister(
                    navController = navControllerRegister,
                    viewModel = viewModel,
                    state = state,
                )
            }
        },
        bottomBar = {
            RegisterBottomBar(
                navController = navController,
                navControllerRegister = navControllerRegister,
                currentRoute = currentRoute ?: RoutesRegister.EmailAndPassword.route,
                viewModel = viewModel
            )
        },
    )
    if (state.isLoading)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.scrim),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    Message(
        message = state.errorMessage,
        visibility = state.isError,
        actionPostDelayed = { viewModel.updateState { copy(isError = isError.not()) } },
        errorIcon = viewModel.getDrawable(ResourceNameKey.ERROR_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)
    )
}

/**
 * Header for the registration screen with a progress bar and title.
 *
 * @param currentRoute Current route in the internal navigation.
 * @param navControllerRegister Internal navigation controller.
 * @param navController Main navigation controller.
 */
@Composable
private fun RegisterHeader(
    currentRoute: String,
    navControllerRegister: NavController,
    navController: NavController,
    viewModel: RegisterViewModel
) {
    val animatedProgress by animateFloatAsState(
        targetValue = calculateProgress(currentRoute),
        animationSpec = tween(durationMillis = 500)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp)
        ) {
            IconButton(
                onClick = {
                    if (currentRoute != RoutesRegister.EmailAndPassword.route)
                        navControllerRegister.popBackStack()
                    else
                        navController.popBackStack()
                },
                colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
            ) {
                Icon(
                    painter = painterResource(viewModel.getDrawable(ResourceNameKey.ARROW_BACK_IOS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)),
                    contentDescription = "Back Screen",
                    modifier = Modifier.size(50.dp),
                    tint = colorScheme.primary
                )
            }
            Text(
                text = Strings.Authentication.REGISTER,
                fontSize = 50.sp,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                color = colorScheme.primary
            )
        }
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxWidth(),
            color = colorScheme.primary,
            trackColor = colorScheme.tertiary,
        )
    }
}

/**
 * Navigation control to advance between screens in the registration flow.
 *
 * @param navController Main navigation controller.
 * @param navControllerRegister Internal navigation controller.
 * @param currentRoute Current route in the internal navigation.
 * @param viewModel ViewModel to handle actions and validations.
 */
@Composable
private fun RegisterBottomBar(
    navController: NavController,
    navControllerRegister: NavController,
    currentRoute: String,
    viewModel: RegisterViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            shape = RectangleShape,
            onClick = {
                when (currentRoute) {
                    RoutesRegister.EmailAndPassword.route ->
                        if (viewModel.validateEmailAndPassword())
                            navControllerRegister.navigate(RoutesRegister.InformationPersonal.route)
                        else
                            viewModel.updateState { copy(isError = isError.not()) }

                    RoutesRegister.InformationPersonal.route ->
                        if (viewModel.validatePersonalInfo())
                            navControllerRegister.navigate(RoutesRegister.Approbation.route)
                        else
                            viewModel.updateState { copy(isError = isError.not()) }

                    RoutesRegister.Approbation.route ->
                        if (viewModel.isTermsAndConditionsChecked()) {
                            viewModel.register(
                                onSuccess = {
                                    navControllerRegister.popBackStack()
                                    navController.navigate(RoutesStart.Start.route)
                                },
                                onError = {
                                    viewModel.updateState { copy(isError = isError.not()) }
                                }
                            )
                        }
                }
            },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = colorScheme.primary
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = Strings.Navigation.NEXT,
                    fontSize = 30.sp,
                    color = colorScheme.primary,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
                )
            }
        }
    }
}

/**
 * Calculates the progress of the progress bar based on the current route.
 *
 * @param route Current route in the internal navigation.
 * @return Progress as a float value between 0 and 1.
 */
@Composable
private fun calculateProgress(
    route: String?
): Float {
    return when (route) {
        RoutesRegister.EmailAndPassword.route -> 0f
        RoutesRegister.InformationPersonal.route -> 0.5f
        RoutesRegister.Approbation.route -> 1f
        else -> 0f
    }
}