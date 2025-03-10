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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.feature.login.state.LoginState
import com.quickness.shared.utils.routes.RoutesStart
import com.shared.resources.drawable.ResourceNameKey
import com.shared.resources.strings.Strings
import com.shared.ui.components.component.LogoAndTitle
import com.shared.ui.components.fields.TextFieldCustomEmail
import com.shared.ui.components.fields.TextFieldCustomPassword
import com.shared.ui.components.helpers.Message
import com.shared.ui.components.helpers.powered
import org.koin.compose.viewmodel.koinViewModel

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
                LogoAndTitle(
                    title = Strings.Authentication.LOGIN,
                    drawableResource = viewModel.getDrawable(ResourceNameKey.LOGOQUICKNESSQC.name)
                )
                Spacer(modifier = Modifier.weight(1f))
                Body(
                    viewModel = viewModel,
                    state = state,
                    navController = navController
                )
                Spacer(modifier = Modifier.weight(1f))
                forgotPassword(navController)
                powered(
                    logo = viewModel.getDrawable(ResourceNameKey.LOGO_SWIFTID_CENTRADO.name),
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
        errorIcon = viewModel.getDrawable(ResourceNameKey.ERROR_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
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
            text = Strings.Authentication.EMAIL,
            onDone = { onDone(navController, viewModel) },
            icons = viewModel.getDrawable(ResourceNameKey.PERSON_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
            onValueChange = {
                if (state.isError) viewModel.update { copy(isError = false) }
                viewModel.update { copy(email = it) }
            }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextFieldCustomPassword(
            value = state.password,
            isError = state.isError,
            text = Strings.Authentication.PASSWORD,
            modifier = Modifier.fillMaxWidth(),
            isPasswordVisible = state.isPasswordVisible,
            togglePasswordVisibility = { viewModel.update { copy(isPasswordVisible = !isPasswordVisible) } },
            onValueChange = {
                if (state.isError) viewModel.update { copy(isError = false) }
                viewModel.update { copy(password = it) }
            },
            onDone = { onDone(navController, viewModel) },
            icon = viewModel.getDrawable(ResourceNameKey.LOCK_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
            togglePasswordVisibilityIcon = {
                viewModel.getDrawable(
                    if (state.isPasswordVisible) ResourceNameKey.VISIBILITY_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name else ResourceNameKey.VISIBILITY_OFF_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name
                )
            }
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
            text = Strings.Authentication.FORGOT_PASSWORD,
            fontSize = 16.sp,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
            color = colorScheme.tertiary
        )
    }
}