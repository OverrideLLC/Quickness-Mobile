<<<<<<<< HEAD:network/api/src/commonMain/kotlin/org/override/quickness/network/api/request/TokensRequest.kt
package org.override.quickness.network.api.request
========
package org.quickness.data.request
>>>>>>>> origin/master:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/data/request/TokensRequest.kt

import kotlinx.serialization.Serializable

/**
 * Representa una solicitud para obtener tokens de un usuario.
 *
 * @property JWT Token de autenticación JWT.
 */
@Serializable
data class TokensRequest(
    val uid: String,
    val root: String
)