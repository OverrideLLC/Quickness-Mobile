package org.override.quickness.feature.biometric

import androidx.compose.runtime.Composable
import org.override.quickness.feature.biometric.BiometricAuth

actual class BiometricAuthImpl actual constructor() : BiometricAuth {
    @Composable
    actual override fun Authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        TODO()
    }
}