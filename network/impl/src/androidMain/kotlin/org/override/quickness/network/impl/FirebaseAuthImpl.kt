package org.override.quickness.network.impl

import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await
import org.override.quickness.network.api.response.AuthResponse
import org.override.quickness.network.api.response.ForgotPasswordResponse
import org.override.quickness.network.api.service.FirebaseAuth

actual class FirebaseAuthImpl : FirebaseAuth {
    private val firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()

    actual override suspend fun signIn(email: String, password: String): AuthResponse {
        return try {
            // Validación de campos vacíos
            if (email.isBlank() || password.isBlank()) {
                throw IllegalArgumentException("El correo electrónico o la contraseña no pueden estar vacíos.")
            }

            Log.v("FirebaseService", "Iniciando sesión con correo: $email")

            // Intentar iniciar sesión con Firebase
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            // Verificar si el usuario existe en el resultado
            result.user?.let { user ->
                Log.v("FirebaseService", "Inicio de sesión exitoso. UID: ${user.uid}")

                // Obtener el token de usuario de manera suspensiva
                val tokenResult = user.getIdToken(true).await()
                val jwt = tokenResult.token

                return if (jwt != null) {
                    Log.v("FirebaseService", "Token de usuario generado: $jwt")
                    AuthResponse(status = "Success", uid = user.uid, jwt = jwt)
                } else {
                    AuthResponse(status = "Failure", message = "No se pudo generar el token.")
                }
            }

            Log.e("FirebaseService", "Usuario no encontrado tras inicio de sesión.")
            AuthResponse(status = "Failure", message = "Usuario no encontrado.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("FirebaseService", "Credenciales inválidas: ${e.message}")
            AuthResponse(
                status = "Failure",
                message = "Credenciales inválidas. Verifique su correo y contraseña."
            )
        } catch (e: FirebaseAuthInvalidUserException) {
            Log.e("FirebaseService", "Usuario no encontrado: ${e.message}")
            AuthResponse(
                status = "Failure",
                message = "Usuario no encontrado. Verifique su correo electrónico."
            )
        } catch (e: Exception) {
            Log.e("FirebaseService", "Error inesperado: ${e.message}")
            AuthResponse(
                status = "Failure",
                message = "Error inesperado: ${e.message}"
            )
        }
    }


    actual override suspend fun forgotPassword(email: String): ForgotPasswordResponse? {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            ForgotPasswordResponse(
                success = true,
                message = "Se ha enviado un correo electrónico para restablecer su contraseña."
            )
        } catch (e: Exception) {
            ForgotPasswordResponse(
                success = false,
                error = "No se pudo enviar el correo electrónico de restablecimiento de contraseña: ${e.message}"
            )
        }
    }

    actual override suspend fun reauthenticateAndChangePassword(
        email: String,
        currentPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            val credential = EmailAuthProvider.getCredential(email, currentPassword)
            user.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        changePassword(
                            newPassword = newPassword,
                            onSuccess = onSuccess,
                            onError = onError
                        )
                    } else {
                        reauthTask.exception?.let { onError(it) }
                    }
                }
        } else {
            onError(Exception("User is not authenticated."))
        }
    }

    actual override fun changePassword(
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            user.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        task.exception?.let { onError(it) }
                    }
                }
        } else {
            onError(Exception("User is not authenticated."))
        }
    }

    actual override fun changeEmail(
        newEmail: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            user.verifyBeforeUpdateEmail(newEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) onSuccess()
                    task.exception?.let { onError(it) }
                }
        } else {
            onError(Exception("User is not authenticated."))
        }
    }

    actual override suspend fun logOut(onSuccess: () -> Unit, onError: (Exception) -> Unit) {}

    actual override suspend fun reauthenticate(
        email: String,
        currentPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            val credential = EmailAuthProvider.getCredential(email, currentPassword)
            user.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        onSuccess()
                    } else {
                        reauthTask.exception?.let { onError(it) }
                    }
                }
        } else {
            onError(Exception("User is not authenticated."))
        }
    }
}