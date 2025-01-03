@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.quickness.data.service

import org.quickness.data.Result.AuthResult
import org.quickness.data.Result.ForgotPasswordResult
import org.quickness.interfaces.plataform.FirebaseAuth

actual class FirebaseAuthImpl : FirebaseAuth {
    actual override suspend fun signIn(
        email: String,
        password: String
    ): AuthResult {
        TODO("Not yet implemented")
    }

    actual override suspend fun forgotPassword(email: String): ForgotPasswordResult? {
        TODO("Not yet implemented")
    }

    actual override suspend fun reauthenticateAndChangePassword(
        email: String,
        currentPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    actual override fun changePassword(
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    actual override fun changeEmail(
        newEmail: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    actual override suspend fun logOut(
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    actual override suspend fun reauthenticate(
        email: String,
        currentPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}