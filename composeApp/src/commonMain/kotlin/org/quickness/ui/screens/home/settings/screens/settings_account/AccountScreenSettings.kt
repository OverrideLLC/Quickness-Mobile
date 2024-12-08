package org.quickness.ui.screens.home.settings.screens.settings_account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.quickness.ui.components.BottomSheetContent
import org.quickness.ui.components.Message
import org.quickness.ui.components.MessageSuccess
import org.quickness.ui.components.SettingsItem
import org.quickness.ui.components.TextFieldCustomEmail
import org.quickness.ui.components.TextFieldCustomPassword
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.alternate_email_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.change_email
import quickness.composeapp.generated.resources.change_password
import quickness.composeapp.generated.resources.confirm_log_out
import quickness.composeapp.generated.resources.email
import quickness.composeapp.generated.resources.log_out
import quickness.composeapp.generated.resources.log_out_warning
import quickness.composeapp.generated.resources.logout_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.new_email
import quickness.composeapp.generated.resources.password
import quickness.composeapp.generated.resources.password_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.request_password_change
import quickness.composeapp.generated.resources.update_email

@Composable
fun AccountScreenSettings() = Screen()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    viewModel: AccountSettingsViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
    ) {
        item {
            SettingsItem(
                name = Res.string.change_password,
                icon = Res.drawable.password_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {
                    scope.launch {
                        viewModel.update { copy(showBottomSheetChangePassword = true) }
                        sheetState.show()
                    }
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.change_email,
                icon = Res.drawable.alternate_email_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {
                    scope.launch {
                        viewModel.update { copy(showBottomSheetChangeEmail = true) }
                        sheetState.show()
                    }
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SettingsItem(
                name = Res.string.log_out,
                icon = Res.drawable.logout_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {
                    scope.launch {
                        viewModel.update { copy(showBottomSheetLogout = true) }
                        sheetState.show()
                    }
                }
            )
        }
    }
    BottomSheets(
        sheetState = sheetState,
        viewModel = viewModel,
        scope = scope,
        state = state
    )
    if (state.isLoading)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheets(
    sheetState: SheetState,
    viewModel: AccountSettingsViewModel,
    scope: CoroutineScope,
    state: AccountSettingsViewModel.AccountState
) {
    BottomSheetContent(
        sheetState = sheetState,
        colorBackground = colorScheme.onBackground.copy(alpha = 0.8f),
        showContent = state.showBottomSheetChangePassword,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(showBottomSheetChangePassword = false) }
            }
        },
        content = {
            ChangePasswordBottomSheet(
                viewModel = viewModel,
                state = state,
                onChangePasswordRequest = { viewModel.changePassword() }
            )
        }
    )

    BottomSheetContent(
        sheetState = sheetState,
        colorBackground = colorScheme.onBackground.copy(alpha = 0.8f),
        showContent = state.showBottomSheetChangeEmail,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(showBottomSheetChangeEmail = false) }
            }
        },
        content = {
            ChangeEmailBottomSheet(
                state = state,
                viewModel = viewModel
            ) {
                viewModel.changeEmail()
            }
        }
    )

    BottomSheetContent(
        sheetState = sheetState,
        colorBackground = colorScheme.onBackground.copy(alpha = 0.8f),
        showContent = state.showBottomSheetLogout,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(showBottomSheetLogout = false) }
            }
        },
        content = { LogOutBottomSheet { viewModel.logOut() } }
    )
    Message(
        visibility = state.isError,
        message = state.message,
        actionPostDelayed = { viewModel.update { copy(isError = false) } },
    )
    MessageSuccess(
        visibility = state.success,
        message = "Password changed successfully",
        actionPostDelayed = { viewModel.update { copy(success = false) } },
    )
}

@Composable
private fun ChangeEmailBottomSheet(
    state: AccountSettingsViewModel.AccountState,
    viewModel: AccountSettingsViewModel,
    onEmailChange: () -> Unit
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.change_email),
            fontFamily = FontFamily(Font(Res.font.Poppins_Medium)),
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCustomEmail(
            value = state.email,
            onValueChange = { viewModel.update { copy(email = it) } },
            text = stringResource(Res.string.email),
            isError = state.isError
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCustomPassword(
            value = state.password,
            onValueChange = { viewModel.update { copy(password = it) } },
            text = stringResource(Res.string.password),
            isError = state.isError,
            isPasswordVisible = state.visibilityPassword,
            togglePasswordVisibility = { viewModel.update { copy(visibilityPassword = !state.visibilityPassword) } }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCustomEmail(
            value = state.newEmail,
            onValueChange = { viewModel.update { copy(newEmail = it) } },
            text = stringResource(Res.string.new_email),
            isError = state.isError
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onEmailChange()
            },
            colors = ButtonDefaults.buttonColors(Color.White)
        ) {
            Text(text = stringResource(Res.string.update_email), color = Color.Black)
        }
    }
}

@Composable
private fun LogOutBottomSheet(onLogOut: () -> Unit) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.log_out),
            fontFamily = FontFamily(Font(Res.font.Poppins_Medium)),
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.log_out_warning),
            color = Color.Gray,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onLogOut() },
            colors = ButtonDefaults.buttonColors(Color.White)
        ) {
            Text(text = stringResource(Res.string.confirm_log_out), color = Color.Black)
        }
    }
}

@Composable
private fun ChangePasswordBottomSheet(
    viewModel: AccountSettingsViewModel,
    state: AccountSettingsViewModel.AccountState,
    onChangePasswordRequest: () -> Unit
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.change_password),
            fontFamily = FontFamily(Font(Res.font.Poppins_Medium)),
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCustomEmail(
            value = state.email,
            onValueChange = { viewModel.update { copy(email = it) } },
            text = stringResource(Res.string.email),
            isError = state.isError
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCustomPassword(
            value = state.password,
            onValueChange = { viewModel.update { copy(password = it) } },
            text = stringResource(Res.string.password),
            isError = state.isError,
            isPasswordVisible = state.visibilityPassword,
            togglePasswordVisibility = { viewModel.update { copy(visibilityPassword = !state.visibilityPassword) } }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCustomPassword(
            value = state.newPassword,
            onValueChange = { viewModel.update { copy(newPassword = it) } },
            text = "New ${stringResource(Res.string.password)}",
            isError = state.isError,
            isPasswordVisible = state.visibilityPassword,
            togglePasswordVisibility = { viewModel.update { copy(visibilityPassword = !visibilityPassword) } }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCustomPassword(
            value = state.confirmPassword,
            onValueChange = { viewModel.update { copy(confirmPassword = it) } },
            text = "Confirm New ${stringResource(Res.string.password)}",
            isError = state.isError,
            isPasswordVisible = state.visibilityPassword,
            togglePasswordVisibility = { viewModel.update { copy(visibilityPassword = !visibilityPassword) } }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onChangePasswordRequest() },
            colors = ButtonDefaults.buttonColors(Color.White)
        ) {
            Text(text = stringResource(Res.string.request_password_change), color = Color.Black)
        }
    }
}