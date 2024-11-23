package org.quickness.data.repository

import org.quickness.data.model.RegisterResult
import org.quickness.data.remote.RegisterService

class RegisterRepository(private val registerService: RegisterService) {
    suspend fun register(
        email: String,
        password: String,
        name: String,
        curp: String,
        phoneNumber: String
    ): RegisterResult {
        return registerService.register(email, password, name, curp, phoneNumber)
    }
}