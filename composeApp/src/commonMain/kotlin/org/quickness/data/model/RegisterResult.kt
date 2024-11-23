package org.quickness.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResult(
    val message: String,
    val uid: String
)