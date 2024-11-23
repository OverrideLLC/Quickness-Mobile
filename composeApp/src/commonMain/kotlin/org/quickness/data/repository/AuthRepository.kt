package org.quickness.data.repository

import org.quickness.data.model.LoginResult
import org.quickness.data.remote.AuthService

class AuthRepository(private val authService: AuthService) {
    suspend fun login(email: String, password: String): LoginResult {
        return authService.login(email, password)
    }
}