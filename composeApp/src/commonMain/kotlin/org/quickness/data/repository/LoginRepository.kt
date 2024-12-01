package org.quickness.data.repository

import org.quickness.data.model.AuthResult
import org.quickness.data.remote.FirebaseService
import org.quickness.encrypt.EncryptPasswordSHA256

class LoginRepository(private val firebaseService: FirebaseService) {

    /**
     * Realiza el inicio de sesión de un usuario autenticando las credenciales proporcionadas.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return [AuthResult] que representa el resultado del intento de inicio de sesión.
     */
    suspend fun login(email: String, password: String): AuthResult? {
        // Encriptar la contraseña antes de enviarla al servicio de Firebase
        val encryptedPassword = EncryptPasswordSHA256.encryptPasswordSHA256(password)

        // Llamada al servicio de autenticación para iniciar sesión
        return firebaseService.signIn(email, encryptedPassword)
    }
}
