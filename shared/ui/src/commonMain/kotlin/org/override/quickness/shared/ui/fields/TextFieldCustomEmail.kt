package org.override.quickness.shared.ui.fields

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.override.quickness.shared.ui.styles.TextFieldColorsApp

@Composable
fun TextFieldCustomEmail(
    value: String,
    isError: Boolean,
    placeholder: String = "example@gmail.com",
    icons: DrawableResource,
    text: String = "Email",
    modifier: Modifier = Modifier,
    onDone: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        maxLines = 1,
        modifier = modifier,
        isError = isError,
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        keyboardOptions = KeyboardOptions().copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
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
