@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.network.impl.service

import com.network.api.response.AuthResponse
import com.network.api.response.ForgotPasswordResponse
import com.network.api.service.FirebaseAuth

actual class FirebaseAuthImpl : FirebaseAuth {
    actual override suspend fun signIn(
        email: String,
        password: String
    ): AuthResponse {
        TODO("Not yet implemented")
    }

    actual override suspend fun forgotPassword(email: String): ForgotPasswordResponse? {
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