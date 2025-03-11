package com.feature.home.plataform

import androidx.compose.runtime.Composable
import com.feature.home.interfaces.BiometricAuth

expect class BiometricAuthImpl() : BiometricAuth {
    @Composable
    override fun Authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}