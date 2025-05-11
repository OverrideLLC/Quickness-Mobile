package org.override.quickness.feature.start.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.override.quickness.feature.start.components.ButtonAccessStart
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.resources.strings.Strings
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.shared.ui.animations.BackgroundAnimated
import org.override.quickness.shared.ui.component.BottomSheetContent
import org.override.quickness.shared.ui.component.LogoAndTitle

@Composable
fun StartScreen(
    navController: NavController,
    contentAuth: @Composable () -> Unit,
    contentRegister: @Composable () -> Unit
) = Screen(navController, contentAuth, contentRegister)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Screen(
    navController: NavController,
    contentAuth: @Composable () -> Unit,
    contentRegister: @Composable () -> Unit,
    viewModel: StartViewModel = koinViewModel<StartViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { targetValue ->
            targetValue == SheetValue.Hidden
        }
    )
    val sheetStateRegister = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { targetValue ->
            targetValue == SheetValue.Hidden
        }
    )
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
            onRegisterClick = {
                scope.launch {
                    sheetState.show()
                    viewModel.update { copy(bottomRegister = true) }
                }
            }
        )
    }
    BottomSheetContent(
        sheetState = sheetState,
        colorBackground = colorScheme.onBackground.copy(alpha = 0.5f),
        showContent = state.bottomLogin,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(bottomLogin = false) }
            }
        },
        content = {
            contentAuth()
        }
    )
    BottomSheetContent(
        sheetState = sheetStateRegister,
        colorBackground = colorScheme.onBackground.copy(alpha = 0.5f),
        showContent = state.bottomRegister,
        onDismiss = {
            scope.launch { sheetStateRegister.hide() }.invokeOnCompletion {
                if (!sheetStateRegister.isVisible) viewModel.update { copy(bottomRegister = false) }
            }
        },
        content = {
            contentRegister()
        },
    )
}