package com.shared.ui.components.fields

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
import com.shared.ui.components.styles.TextFieldColorsApp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TextFIelCustom(
    value: String,
    isError: Boolean,
    modifier: Modifier = Modifier,
    placeholder: String = "example example example example",
    text: String = "Name",
    keyboardType: KeyboardType = KeyboardType.Text,
    icon: DrawableResource,
    onDone: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        maxLines = 1,
        singleLine = true,
        isError = isError,
        modifier = modifier,
        shape = RoundedCornerShape(40.dp),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        keyboardOptions = KeyboardOptions().copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
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