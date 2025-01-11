@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.quickness.plataform

import androidx.compose.runtime.Composable
import org.quickness.interfaces.plataform.BiometricAuth

expect class BiometricAuth(): BiometricAuth {
    @Composable
    override fun Authenticate(onSuccess: () -> Unit, onError: (String) -> Unit)
}