package com.feature.login.screen

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.quickness.shared.utils.routes.RoutesStart
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.quickness.ui.components.component.ButtonAccess
import org.quickness.ui.components.component.LogoAndTitle
import org.quickness.ui.components.fields.TextFieldCustomEmail
import org.quickness.ui.components.fields.TextFieldCustomPassword
import org.quickness.ui.components.helpers.Message
import org.quickness.ui.components.helpers.powered
import org.quickness.ui.states.LoginState
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.email
import quickness.composeapp.generated.resources.forgot_password
import quickness.composeapp.generated.resources.login
import quickness.composeapp.generated.resources.password

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel(),
) = Screen(navController, viewModel)

@Composable
private fun Screen(
    navController: NavController,
    viewModel: LoginViewModel,
) {
    val state by viewModel.state.collectAsState()
    val infiniteTransition = rememberInfiniteTransition()
    val color1 by infiniteTransition.animateColor(
        initialValue = colorScheme.background,
        targetValue = colorScheme.onBackground,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val color2 by infiniteTransition.animateColor(
        initialValue = colorScheme.onBackground,
        targetValue = colorScheme.primary,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing, delayMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        color2,
                        color1,
                        colorScheme.background,
                    )
                )
            )
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                LogoAndTitle(stringResource(Res.string.login))
                Spacer(modifier = Modifier.weight(1f))
                Body(
                    viewModel = viewModel,
                    state = state,
                    navController = navController
                )
                Spacer(modifier = Modifier.weight(1f))
                forgotPassword(navController)
                powered()
                ButtonAccess(
                    onLoginClick = { onDone(navController, viewModel) },
                    onRegisterClick = { navController.navigate(RoutesStart.Register.route) }
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
    if (state.isLoading)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.scrim.copy(alpha = 0.5f))
        ) {
            CircularProgressIndicator()
        }
    Message(
        message = state.errorMessage,
        visibility = state.isError,
        isWarning = state.isWarning,
        actionPostDelayed = {
            viewModel.update { copy(isError = false, isWarning = false, isLoading = false) }
        }
    )
}

@Composable
private fun Body(viewModel: LoginViewModel, state: LoginState, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.wrapContentSize()
    ) {
        TextFieldCustomEmail(
            value = state.email,
            isError = state.isError,
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.email),
            onDone = { onDone(navController, viewModel) },
            onValueChange = {
                if (state.isError) viewModel.update { copy(isError = false) }
                viewModel.update { copy(email = it) }
            }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextFieldCustomPassword(
            value = state.password,
            isError = state.isError,
            text = stringResource(Res.string.password),
            modifier = Modifier.fillMaxWidth(),
            isPasswordVisible = state.isPasswordVisible,
            togglePasswordVisibility = { viewModel.update { copy(isPasswordVisible = !isPasswordVisible) } },
            onValueChange = {
                if (state.isError) viewModel.update { copy(isError = false) }
                viewModel.update { copy(password = it) }
            },
            onDone = { onDone(navController, viewModel) }
        )
    }
}

private fun onDone(
    navController: NavController,
    viewModel: LoginViewModel
) {
    viewModel.login(
        onSuccess = {
            navController.navigate(RoutesStart.Home.route)
            viewModel.update { copy(isLoading = false) }
        },
        onError = {
            viewModel.update {
                copy(
                    isError = true,
                    isWarning = true,
                    errorMessage = it,
                    isLoading = false
                )
            }
        }
    )
}

@Composable
private fun forgotPassword(navController: NavController) {
    TextButton(onClick = { navController.navigate(RoutesStart.ForgotPassword.route) }) {
        Text(
            text = stringResource(Res.string.forgot_password),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(Res.font.Poppins_Medium)),
            color = colorScheme.tertiary
        )
    }
}