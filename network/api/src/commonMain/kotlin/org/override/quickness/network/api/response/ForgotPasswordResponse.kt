<<<<<<<< HEAD:network/api/src/commonMain/kotlin/org/override/quickness/network/api/response/ForgotPasswordResponse.kt
package org.override.quickness.network.api.response
========
package org.quickness.data.response
>>>>>>>> origin/master:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/data/response/ForgotPasswordResponse.kt

data class ForgotPasswordResponse(
    val success: Boolean,
    val message: String? = null,
    val error: String? = null
)