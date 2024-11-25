package org.quickness.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResult(
    val status: String,
    val uid: String
)