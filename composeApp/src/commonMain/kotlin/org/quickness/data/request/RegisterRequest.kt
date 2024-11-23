package org.quickness.data.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val phone_number: String,
    val CURP: String,
)