package com.feature.home.plataform

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.feature.home.interfaces.BiometricAuth
import com.quickness.shared.utils.providers.ContextProvider

actual class BiometricAuthImpl actual constructor() : BiometricAuth {
    @Composable
    actual override fun Authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val context = ContextProvider.getContext()!!
        val fragmentActivity: FragmentActivity = ContextProvider.getFragmentActivity()!!
        val biometricManager = BiometricManager.from(context)

        val authenticators =
            BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL

        when (biometricManager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val executor = ContextCompat.getMainExecutor(context)
                val biometricPrompt = BiometricPrompt(
                    fragmentActivity,
                    executor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            onSuccess()
                        }

                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence
                        ) {
                            onError(errString.toString())
                        }

                        override fun onAuthenticationFailed() {
                            onError("Authentication failed")
                        }
                    }
                )

                val promptInfo = BiometricPrompt.PromptInfo
                    .Builder()
                    .setTitle("Autenticación requerida")
                    .setSubtitle("Usa biometría o tu PIN/contraseña")
                    .setAllowedAuthenticators(authenticators)
                    .build()

                biometricPrompt.authenticate(promptInfo)
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                onSuccess()

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                onSuccess()

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                onSuccess()

            else -> onSuccess()
        }

    }
}