package com.feature.home.plataform

import androidx.compose.runtime.Composable
import com.feature.home.interfaces.BiometricAuth

actual class BiometricAuthImpl actual constructor() : BiometricAuth {
    @Composable
    actual override fun Authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        TODO()
    }
}