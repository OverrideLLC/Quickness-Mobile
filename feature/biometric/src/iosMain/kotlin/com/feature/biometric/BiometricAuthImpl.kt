package com.feature.biometric

import androidx.compose.runtime.Composable

actual class BiometricAuthImpl actual constructor() : BiometricAuth {
    @Composable
    actual override fun Authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        TODO()
    }
}