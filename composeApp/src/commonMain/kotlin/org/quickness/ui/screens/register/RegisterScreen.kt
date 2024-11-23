package org.quickness.ui.screens.register

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.quickness.ui.components.Message
import org.quickness.ui.navegation.NavigationRegister
import org.quickness.utils.RoutesRegister
import org.quickness.utils.RoutesStart
import quickness.composeapp.generated.resources.Poppins_Light
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.arrow_back_ios_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.next
import quickness.composeapp.generated.resources.register

@Composable
fun RegisterScreen(navController: NavController) = Screen(navController)

@OptIn(KoinExperimentalAPI::class)
@Composable
fun Screen(navController: NavController, viewModel: RegisterViewModel = koinViewModel()) {
    val navControllerRegister = rememberNavController()
    val currentBackStackEntry by navControllerRegister.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            Header(
                currentRoute = currentRoute ?: RoutesRegister.EmailAndPassword.route,
                navControllerRegister = navControllerRegister
            )
        },
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
            ) {
                NavigationRegister(navControllerRegister, viewModel)
            }
        },
        bottomBar = {
            ControlNavigation(
                navController = navController,
                navControllerRegister = navControllerRegister,
                currentRoute = currentRoute ?: RoutesRegister.EmailAndPassword.route,
                viewModel = viewModel
            )
        },
        modifier = Modifier.imePadding()
    )

    Message(
        message = viewModel.state.collectAsState().value.errorMessage,
        visibility = viewModel.state.collectAsState().value.isError,
        actionPostDelayed = {
            viewModel.toggleError()
        }
    )
}

@Composable
private fun Header(
    currentRoute: String,
    navControllerRegister: NavController,
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
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    if (
                        currentRoute != RoutesRegister.EmailAndPassword.route
                    ) {
                        navControllerRegister.popBackStack()
                    }else{
                        navControllerRegister.popBackStack()
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.arrow_back_ios_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24),
                    contentDescription = "Atrás",
                    modifier = Modifier.size(50.dp),
                    tint = colorScheme.primary
                )
            }
            Text(
                text = stringResource(Res.string.register),
                fontSize = 50.sp,
                fontFamily = FontFamily(Font(resource = Res.font.Poppins_Light)),
                color = colorScheme.primary
            )
        }
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.fillMaxWidth(),
            color = colorScheme.primary,
            trackColor = colorScheme.tertiary,
        )
    }
}

@Composable
private fun ControlNavigation(
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
                    RoutesRegister.EmailAndPassword.route -> {
                        if (
                            viewModel.isEmailValid() &&
                            viewModel.isPasswordValid() &&
                            viewModel.confirmPassword()
                        ) {
                            navControllerRegister.navigate(
                                RoutesRegister.InformationPersonal.route
                            )
                        } else {
                            viewModel.toggleError()
                        }
                    }

                    RoutesRegister.InformationPersonal.route -> {
                        if (
                            viewModel.isNameValid() &&
                            viewModel.isCurpValid() &&
                            viewModel.isPhoneNumberValid()
                        ) {
                            navControllerRegister.navigate(RoutesRegister.Approbation.route)
                        } else {
                            viewModel.toggleError()
                        }
                    }

                    RoutesRegister.Approbation.route -> {
                        if (viewModel.isTermsAndConditionsChecked()) {
                            viewModel.register(
                                onSuccess = {
                                    navControllerRegister.popBackStack()
                                    navController.navigate(RoutesStart.Start.route)
                                },
                                onError = {
                                    viewModel.toggleError()
                                }
                            )
                        }
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
                    text = stringResource(Res.string.next),
                    fontSize = 30.sp,
                    color = colorScheme.primary,
                    fontFamily = FontFamily(Font(resource = Res.font.Poppins_Light)),
                )
            }
        }
    }
}

@Composable
private fun calculateProgress(route: String?): Float {
    return when (route) {
        RoutesRegister.EmailAndPassword.route -> 0f
        RoutesRegister.InformationPersonal.route -> 0.5f
        RoutesRegister.Approbation.route -> 1f
        else -> 0f
    }
}