package org.quickness.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null,
    val token: String? = null
)