package org.quickness.data.request

import kotlinx.serialization.Serializable

@Serializable
data class TokensRequest(
    val uid: String
)