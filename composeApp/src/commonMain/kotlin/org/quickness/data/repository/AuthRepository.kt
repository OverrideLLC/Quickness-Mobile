package org.quickness.data.repository

import org.quickness.data.Result.ApiResponse
import org.quickness.data.Result.AuthResult
import org.quickness.data.request.AuthUserRequest
import org.quickness.data.service.AuthUserService
import org.quickness.data.service.FirebaseService

class AuthRepository(
    private val firebaseService: FirebaseService,
    private val authService: AuthUserService
) {
    /**
     * Realiza el inicio de sesión de un usuario autenticando las credenciales proporcionadas.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return [AuthResult] que representa el resultado del intento de inicio de sesión.
     */
    suspend fun login(email: String, password: String): AuthResult {
        // Llamada al servicio de autenticación para iniciar sesión
        return firebaseService.signIn(email, password)
    }

    suspend fun jwt(token: String): ApiResponse {
        return authService.jwt(AuthUserRequest(token))
    }
}
