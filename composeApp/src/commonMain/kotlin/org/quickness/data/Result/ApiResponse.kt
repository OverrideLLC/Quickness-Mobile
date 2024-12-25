package org.quickness.data.Result

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ApiResponse(
    val message: String,
    val status: Int,
    val data: JsonObject
)

