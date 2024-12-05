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

actual class FirebaseService : FirebaseAuth, FirebaseFirestore {
    private val firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
    private val firebaseFirestore = com.google.firebase.firestore.FirebaseFirestore.getInstance()

    actual override suspend fun signIn(email: String, password: String): AuthResult? {
        return try {
            if (email.isBlank() || password.isBlank()) {
                throw IllegalArgumentException("El correo electrónico o la contraseña no pueden estar vacíos.")
            }
            Log.v("FirebaseService", "signIn: $email $password")
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            Log.v("FirebaseService", "Sigue aquí")
            user?.let {
                return@let AuthResult(status = "Success", uid = it.uid)
            } ?: run {
                return@run AuthResult(status = "Failure", message = "Usuario no encontrado.")
            }
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            return AuthResult(
                status = "Failure",
                message = "Credenciales inválidas. Verifique su correo y contraseña."
            )
        } catch (e: FirebaseAuthInvalidUserException) {
            return AuthResult(
                status = "Failure",
                message = "Usuario no encontrado. Verifique su correo electrónico."
            )
        } catch (e: Exception) {
            return AuthResult(status = "Failure", message = "Error inesperado: ${e.message}")
        }
    }

    actual override suspend fun getData(): DataFirestore {
        // Definición de la función de recuperación de datos
        return try {
            // Obtén la referencia a la colección y documento específico
            val documentSnapshot = firebaseFirestore.collection("Users")
                .document(
                    SharedPreference().getString(
                        UID_KEY,
                        ""
                    )
                ) // Asegúrate de tener el ID correcto del documento
                .get()
                .await() // Espera de forma asincrónica el resultado de la llamada

            // Verifica si el documento existe y extrae el campo "credits"
            if (documentSnapshot.exists()) {
                val credits = documentSnapshot.get("balance") ?: 0 // Valor pordefecto si no existe
                SharedPreference().setString("credits", credits.toString())
                DataFirestore(credits.toString())
            } else {
                throw Exception("Documento no encontrado")
            }
        } catch (e: Exception) {
            // Manejo de errores
            throw Exception("Error al obtener los datos: ${e.message}", e)
        }
    }

    actual override suspend fun updateField(fieldName: String, value: Any) {
        try {
            // Obtén el UID del usuario
            val userId = SharedPreference().getString(UID_KEY, "")
            if (userId.isNullOrEmpty()) {
                throw IllegalArgumentException("El UID no puede estar vacío")
            }

            // Actualiza el campo específico
            firebaseFirestore.collection("Users")
                .document(userId)
                .update(fieldName, value) // Actualiza el campo con el valor proporcionado
                .await() // Espera de forma asincrónica a que se complete
        } catch (e: Exception) {
            // Manejo de errores
            throw Exception("Error al actualizar el campo: ${e.message}", e)
        }
    }



}