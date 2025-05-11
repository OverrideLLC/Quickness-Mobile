package org.override.quickness.network.api.response

data class ForgotPasswordResponse(
    val success: Boolean,
    val message: String? = null,
    val error: String? = null
)