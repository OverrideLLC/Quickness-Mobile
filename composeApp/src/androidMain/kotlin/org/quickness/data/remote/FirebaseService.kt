package org.quickness.data.remote

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await
import org.quickness.data.firebase.FirebaseAuth
import org.quickness.data.model.AuthResult

actual class FirebaseService : FirebaseAuth {
    private val firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
    override suspend fun signIn(email: String, password: String): AuthResult? {
        return try {
            if (email.isBlank() || password.isBlank()) {
                throw IllegalArgumentException("El correo electrónico o la contraseña no pueden estar vacíos.")
            }

            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user

            user?.let {
                AuthResult(status = "Success", uid = it.uid)
            } ?: run {
                AuthResult(status = "Failure", message = "Usuario no encontrado.")
            }
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            AuthResult(
                status = "Failure",
                message = "Credenciales inválidas. Verifique su correo y contraseña."
            )
        } catch (e: FirebaseAuthInvalidUserException) {
            AuthResult(
                status = "Failure",
                message = "Usuario no encontrado. Verifique su correo electrónico."
            )
        } catch (e: Exception) {
            AuthResult(status = "Failure", message = "Error inesperado: ${e.message}")
        }
    }

}