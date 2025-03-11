package com.feature.login.screen

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
import com.feature.login.components.Body
import com.feature.login.components.Header
import com.shared.resources.drawable.ResourceNameKey
import com.shared.ui.components.animations.BackgroundAnimated
import com.shared.ui.components.component.Progress
import com.shared.ui.components.helpers.Message
import org.koin.compose.viewmodel.koinViewModel
import org.quickness.ui.components.component.ButtonAccess

@Composable
fun LoginScreen() = Screen(viewModel = koinViewModel())

@Composable
internal fun Screen(
    viewModel: LoginViewModel,
) {
    val state by viewModel.state.collectAsState()

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
                            onSuccess = {},
                            onError = { message ->
                                viewModel.update { copy(isError = true, errorMessage = message) }
                            }
                        )
                    },
                    onRegisterClick = {  }
                )
            }
        }
    }
    Progress(state.isLoading)
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