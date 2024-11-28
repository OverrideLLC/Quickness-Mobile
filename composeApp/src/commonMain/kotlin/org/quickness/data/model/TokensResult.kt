package org.quickness.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TokensResult(
    val status: String,
    val tokens: Map<String, String>,
)