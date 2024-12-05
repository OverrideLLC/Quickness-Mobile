package org.quickness.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await
import org.quickness.SharedPreference
import org.quickness.data.model.AuthResult
import org.quickness.data.model.DataFirestore
import org.quickness.interfaces.FirebaseAuth
import org.quickness.interfaces.FirebaseFirestore
import org.quickness.utils.`object`.KeysCache.UID_KEY

actual class FirebaseService : FirebaseAuth {
    private val firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()

    actual override suspend fun signIn(email: String, password: String): AuthResult? {
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
                return AuthResult(status = "Success", uid = user.uid)
            }

            // Si `user` es nulo, algo falló
            Log.e("FirebaseService", "Usuario no encontrado tras inicio de sesión.")
            return AuthResult(status = "Failure", message = "Usuario no encontrado.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("FirebaseService", "Credenciales inválidas: ${e.message}")
            return AuthResult(
                status = "Failure",
                message = "Credenciales inválidas. Verifique su correo y contraseña."
            )
        } catch (e: FirebaseAuthInvalidUserException) {
            Log.e("FirebaseService", "Usuario no encontrado: ${e.message}")
            return AuthResult(
                status = "Failure",
                message = "Usuario no encontrado. Verifique su correo electrónico."
            )
        } catch (e: Exception) {
            Log.e("FirebaseService", "Error inesperado: ${e.message}")
            return AuthResult(
                status = "Failure",
                message = "Error inesperado: ${e.message}"
            )
        }
    }
}