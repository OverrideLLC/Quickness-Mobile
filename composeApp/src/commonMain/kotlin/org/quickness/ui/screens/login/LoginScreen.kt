package org.quickness.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.quickness.ui.components.ButtonAccess
import org.quickness.ui.components.LogoAndTitle
import org.quickness.ui.components.Message
import org.quickness.ui.components.TextFieldCustomEmail
import org.quickness.ui.components.TextFieldCustomPassword
import org.quickness.ui.components.powered
import org.quickness.utils.routes.RoutesStart
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.email
import quickness.composeapp.generated.resources.forgot_password
import quickness.composeapp.generated.resources.login
import quickness.composeapp.generated.resources.password

@OptIn(KoinExperimentalAPI::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) = Screen(navController, viewModel)

@Composable
private fun Screen(navController: NavController, viewModel: LoginViewModel) {
    var errorString by remember { mutableStateOf("") }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(horizontal = 40.dp, vertical = 40.dp)
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (viewModel.isLoading.collectAsState().value) {
                    CircularProgressIndicator()
                }
                Spacer(modifier = Modifier.weight(1f))
                LogoAndTitle(stringResource(Res.string.login))
                Spacer(modifier = Modifier.weight(1f))
                Body(viewModel)

                Spacer(modifier = Modifier.weight(1f))
                forgotPassword(navController)
                powered()
                ButtonAccess(
                    onLoginClick = {
                        viewModel.login(
                            onSuccess = { navController.navigate(RoutesStart.Home.route) },
                            onError = { errorString = it }
                        )
                    },
                    onRegisterClick = { navController.navigate(RoutesStart.Register.route) }
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
    Message(
        message = errorString,
        visibility = viewModel.isError.collectAsState().value,
        isWarning = viewModel.isWarning.collectAsState().value,
        actionPostDelayed = {
            viewModel.toggleError()
        }
    )
}

@Composable
private fun Body(viewModel: LoginViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.wrapContentSize()
    ) {
        TextFieldCustomEmail(
            value = viewModel.email.collectAsState().value,
            isError = viewModel.isError.collectAsState().value,
            text = stringResource(Res.string.email),
            onValueChange = { viewModel.onEmailChange(it) }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextFieldCustomPassword(
            value = viewModel.password.collectAsState().value,
            isError = viewModel.isError.collectAsState().value,
            text = stringResource(Res.string.password),
            isPasswordVisible = viewModel.isPasswordVisible.collectAsState().value,
            togglePasswordVisibility = { viewModel.togglePasswordVisibility() },
            onValueChange = { viewModel.onPasswordChange(it) }
        )
    }
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