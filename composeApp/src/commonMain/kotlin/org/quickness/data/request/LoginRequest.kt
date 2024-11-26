package org.quickness.data.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val idtoken: String
)
