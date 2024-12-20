package org.quickness.data.Result

data class ForgotPasswordResult(
    val success: Boolean,
    val message: String? = null,
    val error: String? = null
)