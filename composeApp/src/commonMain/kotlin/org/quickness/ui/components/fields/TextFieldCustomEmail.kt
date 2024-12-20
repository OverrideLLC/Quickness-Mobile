package org.quickness.ui.components.fields

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
import org.quickness.ui.components.TextFieldColorsApp
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.alternate_email_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24

@Composable
fun TextFieldCustomEmail(
    value: String,
    isError: Boolean,
    placeholder: String = "example@gmail.com",
    icons: DrawableResource = Res.drawable.alternate_email_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
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
