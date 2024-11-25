package org.quickness.data.repository

import org.quickness.data.model.AuthResult
import org.quickness.data.remote.FirebaseService
import org.quickness.encrypt.EncryptPasswordSHA256

class LoginRepository(private val firebaseService: FirebaseService) {
    suspend fun login(email: String, password: String): AuthResult? {
        return firebaseService.signIn(email, EncryptPasswordSHA256.encryptPasswordSHA256(password))
    }
}