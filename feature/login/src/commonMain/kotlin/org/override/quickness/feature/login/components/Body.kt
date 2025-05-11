package org.override.quickness.feature.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.override.quickness.feature.login.screen.LoginViewModel
import org.override.quickness.feature.login.state.LoginState
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.resources.strings.Strings
import org.override.quickness.shared.ui.fields.TextFieldCustomEmail
import org.override.quickness.shared.ui.fields.TextFieldCustomPassword
import org.override.quickness.shared.ui.helpers.powered

@Composable
internal fun Body(viewModel: LoginViewModel, state: LoginState) {
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
            onDone = { viewModel.onDone() },
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
            onDone = { viewModel.onDone() },
            icon = viewModel.getDrawable(ResourceNameKey.LOCK_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name),
            togglePasswordVisibilityIcon = {
                viewModel.getDrawable(
                    if (state.isPasswordVisible) ResourceNameKey.VISIBILITY_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name else ResourceNameKey.VISIBILITY_OFF_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name
                )
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        forgotPassword()
        powered(logo = viewModel.getDrawable(ResourceNameKey.LOGO_SWIFTID_CENTRADO.name))
        Spacer(modifier = Modifier.weight(1f))
    }
}
