package org.quickness.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.alternate_email_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.border_color_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.password_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.visibility_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
import quickness.composeapp.generated.resources.visibility_off_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24

@Composable
fun TextFieldCustomEmail(
    value: String,
    isError: Boolean,
    placeholder: String = "example@gmail.com",
    icons: DrawableResource = Res.drawable.alternate_email_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
    text: String = "Email",
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        maxLines = 1,
        modifier = modifier,
        isError = isError,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        placeholder = { Text(placeholder) },
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(40.dp),
        label = { Text(text) },
        leadingIcon = {
            Icon(
                painterResource(icons),
                contentDescription = "Icons",
                modifier = Modifier.size(25.dp)
            )
        },
        colors = TextFieldColorsApp()
    )
}

@Composable
fun TextFieldCustomPassword(
    value: String,
    isError: Boolean,
    placeholder: String = "12345678",
    text: String = "Password",
    modifier: Modifier = Modifier,
    icon: DrawableResource = Res.drawable.password_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
    isPasswordVisible: Boolean,
    togglePasswordVisibility: () -> Unit,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        maxLines = 1,
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = isError,
        label = { Text(text) },
        shape = RoundedCornerShape(40.dp),
        leadingIcon = {
            Icon(
                painterResource(icon),
                contentDescription = "Icons",
                modifier = Modifier.size(25.dp),
            )
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = { Text(placeholder) },
        colors = TextFieldColorsApp(),
        trailingIcon = {
            IconButton(
                onClick = { togglePasswordVisibility() },
                content = {
                    Icon(
                        painter = painterResource(
                            if (isPasswordVisible) Res.drawable.visibility_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24 else Res.drawable.visibility_off_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24
                        ),
                        contentDescription = "Password",
                        tint = if (isError) colorScheme.error else if (isPasswordVisible) colorScheme.primary else colorScheme.tertiary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    )
}

@Composable
fun TextFIelCustom(
    value: String,
    isError: Boolean,
    modifier: Modifier = Modifier,
    placeholder: String = "example example example example",
    text: String = "Name",
    keyboardType: KeyboardType = KeyboardType.Text,
    icon: DrawableResource = Res.drawable.border_color_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        maxLines = 1,
        singleLine = true,
        isError = isError,
        modifier = modifier,
        shape = RoundedCornerShape(40.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        leadingIcon = {
            Icon(
                painterResource(icon),
                contentDescription = "Icons",
                modifier = Modifier.size(25.dp)
            )
        },
        placeholder = { Text(placeholder) },
        onValueChange = { onValueChange(it) },
        label = { Text(text) },
        colors = TextFieldColorsApp()
    )
}