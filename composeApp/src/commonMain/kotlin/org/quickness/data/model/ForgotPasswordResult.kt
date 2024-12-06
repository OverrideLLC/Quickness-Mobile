package org.quickness.data.model

data class ForgotPasswordResult(
    val success: Boolean,
    val message: String? = null,
    val error: String? = null
)