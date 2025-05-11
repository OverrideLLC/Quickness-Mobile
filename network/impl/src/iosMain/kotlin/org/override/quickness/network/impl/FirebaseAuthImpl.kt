package org.override.quickness.network.impl

<<<<<<<< HEAD:network/impl/src/iosMain/kotlin/org/override/quickness/network/impl/FirebaseAuthImpl.kt
import org.override.quickness.network.api.response.AuthResponse
import org.override.quickness.network.api.response.ForgotPasswordResponse
import org.override.quickness.network.api.service.FirebaseAuth
========
package org.quickness.data.service

import org.quickness.data.response.AuthResponse
import org.quickness.data.response.ForgotPasswordResponse
import org.quickness.interfaces.plataform.FirebaseAuth
>>>>>>>> origin/master:composeApp/src/iosMain/kotlin/org/override/quickness/data/service/FirebaseAuthImpl.kt

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