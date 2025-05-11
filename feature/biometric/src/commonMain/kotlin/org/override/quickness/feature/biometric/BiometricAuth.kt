package org.override.quickness.feature.biometric

import androidx.compose.runtime.Composable

interface BiometricAuth {
    @Composable
    fun Authenticate(onSuccess: () -> Unit, onError: (String) -> Unit)
}