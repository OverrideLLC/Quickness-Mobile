@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.quickness.plataform

import androidx.compose.runtime.Composable
import org.quickness.interfaces.plataform.BiometricAuth

actual class BiometricAuthImpl actual constructor() : BiometricAuth {
    @Composable
    actual override fun Authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

    }
}