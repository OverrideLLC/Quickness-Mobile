package com.feature.start.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.feature.start.components.ButtonAccessStart
import com.quickness.shared.utils.routes.RoutesStart
import com.shared.resources.drawable.ResourceNameKey
import com.shared.resources.strings.Strings
import com.shared.ui.components.animations.BackgroundAnimated
import com.shared.ui.components.component.LogoAndTitle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StartScreen(navController: NavController) = Screen(navController)

@Composable
internal fun Screen(
    navController: NavController,
    viewModel: StartViewModel = koinViewModel<StartViewModel>()
) {
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
            onLoginClick = { navController.navigate(RoutesStart.Login.route) },
            onRegisterClick = { navController.navigate(RoutesStart.Register.route) }
        )
    }
}