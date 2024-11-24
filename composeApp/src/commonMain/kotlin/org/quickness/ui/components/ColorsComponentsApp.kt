package org.quickness.ui.components

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun TextFieldColorsApp(): TextFieldColors = TextFieldDefaults.colors(
    focusedTextColor = colorScheme.primary,
    unfocusedTextColor = colorScheme.tertiary,
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    focusedIndicatorColor = colorScheme.primary,
    unfocusedIndicatorColor = colorScheme.tertiary,
    focusedLabelColor = colorScheme.primary,
    unfocusedLabelColor = colorScheme.tertiary,
    cursorColor = colorScheme.primary,
    focusedPlaceholderColor = colorScheme.primary,
    unfocusedPlaceholderColor = colorScheme.tertiary,
    disabledPlaceholderColor = colorScheme.tertiary,
    errorIndicatorColor = colorScheme.error,
    errorLabelColor = colorScheme.error,
    errorLeadingIconColor = colorScheme.error,
    errorCursorColor = colorScheme.error,
    errorTrailingIconColor = colorScheme.error,
    errorSupportingTextColor = colorScheme.error,
    errorTextColor = colorScheme.error,
    errorPlaceholderColor = colorScheme.error,
    errorContainerColor = Color.Transparent,
    focusedLeadingIconColor = colorScheme.primary,
    unfocusedLeadingIconColor = colorScheme.tertiary,
    focusedTrailingIconColor = colorScheme.primary,
    unfocusedTrailingIconColor = colorScheme.tertiary
)