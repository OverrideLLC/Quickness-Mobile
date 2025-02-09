package org.quickness.plataform

import androidx.compose.runtime.Composable
import org.quickness.interfaces.plataform.BiometricAuth

expect class BiometricAuthImpl(): BiometricAuth {
    @Composable
    override fun Authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}