package com.feature.biometric

import androidx.compose.runtime.Composable

expect class BiometricAuthImpl() : BiometricAuth {
    @Composable
    override fun Authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}