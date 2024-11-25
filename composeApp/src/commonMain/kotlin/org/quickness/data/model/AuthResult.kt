package org.quickness.data.model

data class AuthResult(
    val status: String,
    val uid: String? = null,
    val message: String? = null
)
