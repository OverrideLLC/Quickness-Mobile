package com.feature.login.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.shared.resources.strings.Strings

@Composable
internal fun forgotPassword() {
    TextButton(onClick = { }) {
        Text(
            text = Strings.Authentication.FORGOT_PASSWORD,
            fontSize = 16.sp,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
            color = colorScheme.tertiary
        )
    }
}