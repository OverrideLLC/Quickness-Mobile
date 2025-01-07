package org.quickness.data.service

import org.quickness.data.response.AuthResponse
import org.quickness.data.response.ForgotPasswordResponse
import org.quickness.interfaces.plataform.FirebaseAuth

/**
 * Servicio de autenticación Firebase para Kotlin Multiplatform.
 * Define las expectativas para la implementación de autenticación en cada plataforma.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class FirebaseAuthImpl() : FirebaseAuth {
    override suspend fun signIn(email: String, password: String): AuthResponse
    override suspend fun forgotPassword(email: String): ForgotPasswordResponse?
    override suspend fun reauthenticateAndChangePassword(email: String, currentPassword: String, newPassword: String, onSuccess: () -> Unit, onError: (Exception) -> Unit)
    override fun changePassword(newPassword: String, onSuccess: () -> Unit, onError: (Exception) -> Unit)
    override fun changeEmail(newEmail: String, onSuccess: () -> Unit, onError: (Exception) -> Unit)
    override suspend fun logOut(onSuccess: () -> Unit, onError: (Exception) -> Unit)
    override suspend fun reauthenticate(email: String, currentPassword: String, onSuccess: () -> Unit, onError: (Exception) -> Unit)
}
