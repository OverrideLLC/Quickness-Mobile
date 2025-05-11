<<<<<<<< HEAD:network/api/src/commonMain/kotlin/org/override/quickness/network/api/response/ApiResponse.kt
package org.override.quickness.network.api.response
========
package org.quickness.data.response
>>>>>>>> origin/master:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/data/response/ApiResponse.kt

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ApiResponse(
    val message: String,
    val status: Int,
    val data: JsonObject
)

