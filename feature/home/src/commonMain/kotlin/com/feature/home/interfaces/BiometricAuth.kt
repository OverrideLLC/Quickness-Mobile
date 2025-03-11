package com.feature.home.interfaces

import androidx.compose.runtime.Composable

interface BiometricAuth {
    @Composable
    fun Authenticate(onSuccess: () -> Unit, onError: (String) -> Unit)
}