package org.override.quickness.network.api.request

import kotlinx.serialization.Serializable

@Serializable
data class ApolloRequestQr(
    val token: String,
    val uid: String
)
