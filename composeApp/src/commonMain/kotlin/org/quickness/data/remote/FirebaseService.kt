package org.quickness.data.remote

import org.quickness.interfaces.FirebaseAuth
import org.quickness.data.model.AuthResult

/**
 * Servicio de autenticación Firebase para Kotlin Multiplatform.
 * Define las expectativas para la implementación de autenticación en cada plataforma.
 */
expect class FirebaseService() : FirebaseAuth {
    /**
     * Realiza el inicio de sesión en Firebase con las credenciales proporcionadas.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return [AuthResult] con el estado y la información del usuario, o `null` si falla.
     */
    override suspend fun signIn(email: String, password: String): AuthResult?
}
