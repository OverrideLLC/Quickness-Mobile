package com.feature.start.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.feature.login.screen.LoginScreen
import com.feature.start.components.ButtonAccessStart
import com.quickness.shared.utils.routes.RoutesStart
import com.shared.resources.drawable.ResourceNameKey
import com.shared.resources.strings.Strings
import com.shared.ui.components.animations.BackgroundAnimated
import com.shared.ui.components.component.BottomSheetContent
import com.shared.ui.components.component.LogoAndTitle
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StartScreen(navController: NavController) = Screen(navController)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Screen(
    navController: NavController,
    viewModel: StartViewModel = koinViewModel<StartViewModel>()
) {
    val state by remember { viewModel.state }.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = BackgroundAnimated(
                    colorPrimary = colorScheme.primaryContainer,
                    colorSecondary = colorScheme.background
                )
            )
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LogoAndTitle(
            title = Strings.GeneralAppStrings.APP_NAME,
            drawableResource = viewModel.getDrawable(ResourceNameKey.LOGOQUICKNESSQC.name)
        )
        Spacer(modifier = Modifier.weight(1f))
        ButtonAccessStart(
            viewModel = viewModel,
            onLoginClick = {
                scope.launch {
                    sheetState.show()
                    viewModel.update { copy(bottomLogin = true) }
                }
            },
            onRegisterClick = { navController.navigate(RoutesStart.Register.route) }
        )
    }
    BottomSheetContent(
        sheetState = sheetState,
        colorBackground = colorScheme.background,
        showContent = state.bottomLogin,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(bottomLogin = false) }
            }
        },
        content = {
            LoginScreen()
        }
    )
    if (state.bottomRegister) {
        BottomSheetContent(
            sheetState = sheetState,
            colorBackground = colorScheme.background,
            showContent = state.bottomRegister,
            onDismiss = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) viewModel.update { copy(bottomRegister = false) }
                }
            },
            content = {

            },
        )
    }
}