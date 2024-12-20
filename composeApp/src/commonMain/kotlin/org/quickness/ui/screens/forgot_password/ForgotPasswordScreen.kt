package org.quickness.ui.screens.forgot_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.quickness.ui.components.helpers.MessageSuccess
import org.quickness.ui.components.fields.TextFieldCustomEmail
import quickness.composeapp.generated.resources.Poppins_Medium
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.alternate_email_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.forgot_password_removebg_preview
import quickness.composeapp.generated.resources.start_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24

@Composable
fun ForgotPasswordScreen() = Screen()

@Composable
private fun Screen(viewModel: ForgotPasswordViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsState().value
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
            .imePadding(),
    ) {
        item {
            Image(
                painter = painterResource(Res.drawable.forgot_password_removebg_preview),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                colorScheme.primary,
                                colorScheme.onBackground,
                            )
                        ),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextFieldCustomEmail(
                value = state.email,
                onValueChange = {
                    viewModel.update { copy(email = it) }
                },
                placeholder = "Enter your email",
                icons = Res.drawable.alternate_email_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                isError = false,
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    viewModel.reset()
                },
                modifier = Modifier.height(50.dp).fillMaxWidth().padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.tertiary,
                    contentColor = colorScheme.secondary
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = "Reset Password",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(resource = Res.font.Poppins_Medium)),
                        color = colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(Res.drawable.start_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24),
                        contentDescription = "Send Email",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
    MessageSuccess(
        visibility = state.success,
        message = "Se ha enviado un correo electrónico para restablecer su contraseña.",
        actionPostDelayed = { viewModel.update { copy(success = false) } }
    )
    MessageSuccess(
        visibility = state.error,
        message = "No se pudo enviar el correo electrónico de restablecimiento de contraseña.",
        actionPostDelayed = { viewModel.update { copy(error = false) } }
    )
}