package org.override.quickness.shared.ui.fields

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.override.quickness.shared.ui.styles.TextFieldColorsApp

@Composable
fun TextFieldCustomPassword(
    value: String,
    isError: Boolean,
    placeholder: String = "12345678",
    text: String = "Password",
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    isPasswordVisible: Boolean,
    togglePasswordVisibilityIcon: () -> DrawableResource,
    onDone: () -> Unit = {},
    togglePasswordVisibility: () -> Unit,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        maxLines = 1,
        modifier = modifier,
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        keyboardOptions = KeyboardOptions().copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
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
                        painter = painterResource(togglePasswordVisibilityIcon()),
                        contentDescription = "Password",
                        tint = if (isError) colorScheme.error else if (isPasswordVisible) colorScheme.primary else colorScheme.tertiary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    )
}