package org.quickness.interfaces

import org.quickness.data.model.AuthResult

interface FirebaseAuth {
    suspend fun signIn(email: String, password: String): AuthResult?
}