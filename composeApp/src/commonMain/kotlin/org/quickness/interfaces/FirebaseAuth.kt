package org.quickness.interfaces

import org.quickness.data.model.AuthResult
import org.quickness.data.model.ForgotPasswordResult

/**
 * Interfaz que define la funcionalidad de autenticación de Firebase.
 *
 * Esta interfaz debe ser implementada por cualquier servicio que maneje la autenticación de usuarios,
 * como un servicio de inicio de sesión que interactúe con Firebase para verificar credenciales.
 */
interface FirebaseAuth {
    /**
     * Inicia sesión en la aplicación utilizando el correo electrónico y la contraseña proporcionados.
     *
     * @param email La dirección de correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return Un objeto [AuthResult] que representa el resultado de la autenticación,
     *         o `null` si la autenticación falla o no se puede completar.
     */
    suspend fun signIn(email: String, password: String): AuthResult?

    suspend fun forgotPassword(email: String): ForgotPasswordResult?

    suspend fun reauthenticateAndChangePassword(
        email: String,
        currentPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    fun changePassword(newPassword: String, onSuccess: () -> Unit, onError: (Exception) -> Unit)

    fun changeEmail(newEmail: String, onSuccess: () -> Unit, onError: (Exception) -> Unit)

    suspend fun logOut(onSuccess: () -> Unit, onError: (Exception) -> Unit)

    suspend fun reauthenticate(
        email: String,
        currentPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )
}
