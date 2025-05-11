package org.override.quickness.feature.biometric

import androidx.compose.runtime.Composable
import org.override.quickness.feature.biometric.BiometricAuth

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class BiometricAuthImpl() : BiometricAuth {
    @Composable
    override fun Authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}