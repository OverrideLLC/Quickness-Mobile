package com.feature.biometric

import androidx.compose.runtime.Composable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class BiometricAuthImpl() : BiometricAuth {
    @Composable
    override fun Authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}