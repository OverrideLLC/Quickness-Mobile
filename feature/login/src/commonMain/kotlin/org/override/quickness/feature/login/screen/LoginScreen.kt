package org.override.quickness.feature.login.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.login.components.Body
import org.override.quickness.feature.login.components.ButtonAccess
import org.override.quickness.feature.login.components.Header
import org.override.quickness.feature.login.state.LoginState
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.ui.animations.BackgroundAnimated
import org.override.quickness.shared.ui.component.Progress
import org.override.quickness.shared.ui.helpers.Message
import org.override.quickness.shared.utils.routes.RoutesStart

@Deprecated("It was replaced by WebView")
@Composable
fun LoginScreen(
    navController: NavController
) = Screen(viewModel = koinViewModel(), navController = navController)

@Composable
internal fun Screen(
    viewModel: LoginViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    Crossfade(
        targetState = state.isLoading,
        label = "LoginScreen"
    ) { target ->
        if (!target) {
            Content(
                viewModel = viewModel,
                navController = navController,
                state = state
            )
        } else {
            Progress(isVisible = state.isLoading)
        }
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
internal fun Content(
    viewModel: LoginViewModel,
    navController: NavController,
    state: LoginState
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = BackgroundAnimated(
                    colorPrimary = colorScheme.background,
                    colorSecondary = colorScheme.primaryContainer
                )
            )
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(16.dp).imePadding()
            ) {
                Header(viewModel = viewModel)
                Body(
                    viewModel = viewModel,
                    state = state,
                )
                ButtonAccess(
                    viewModel = viewModel,
                    onLoginClick = {
                        viewModel.login(
                            onSuccess = {
                                navController.navigate(RoutesStart.Home.route)
                            },
                            onError = { message ->
                                viewModel.update { copy(isError = true, errorMessage = message) }
                            }
                        )
                    },
                    onRegisterClick = { }
                )
            }
        }
    }
}