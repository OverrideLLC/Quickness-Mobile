package org.quickness.ui.screens.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.stringResource
import org.quickness.ui.components.ButtonAccess
import org.quickness.ui.components.LogoAndTitle
import org.quickness.ui.components.powered
import org.quickness.utils.RoutesStart
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.app_name

@Composable
fun StartScreen(navController: NavController) = Screen(navController)

@Composable
private fun Screen(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(horizontal = 40.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LogoAndTitle(stringResource(Res.string.app_name))
        Spacer(modifier = Modifier.weight(1f))
        powered()
        ButtonAccess(
            onLoginClick = { navController.navigate(RoutesStart.Login.route) },
            onRegisterClick = { navController.navigate(RoutesStart.Register.route) }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}