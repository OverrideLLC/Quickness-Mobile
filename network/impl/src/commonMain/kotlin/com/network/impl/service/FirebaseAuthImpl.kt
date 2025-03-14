package com.network.impl.service

import com.network.api.response.AuthResponse
import com.network.api.response.ForgotPasswordResponse
import com.network.api.service.FirebaseAuth

/**
 * Servicio de autenticación Firebase para Kotlin Multiplatform.
 * Define las expectativas para la implementación de autenticación en cada plataforma.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class FirebaseAuthImpl() : FirebaseAuth {
    override suspend fun signIn(email: String, password: String): AuthResponse
    override suspend fun forgotPassword(email: String): ForgotPasswordResponse?
    override suspend fun reauthenticateAndChangePassword(
        email: String,
        currentPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    override fun changePassword(
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    override fun changeEmail(newEmail: String, onSuccess: () -> Unit, onError: (Exception) -> Unit)
    override suspend fun logOut(onSuccess: () -> Unit, onError: (Exception) -> Unit)
    override suspend fun reauthenticate(
        email: String,
        currentPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )
}
