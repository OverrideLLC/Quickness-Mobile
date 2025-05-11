<<<<<<<< HEAD:network/api/src/commonMain/kotlin/org/override/quickness/network/api/request/AuthUserRequest.kt
package org.override.quickness.network.api.request
========
package org.quickness.data.request
>>>>>>>> origin/master:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/data/request/AuthUserRequest.kt

import kotlinx.serialization.Serializable

/**
 * Representa una solicitud de inicio de sesión que contiene el token de identificación del usuario.
 *
 * @property token Token de identificación del usuario para autenticar la solicitud.
 */
@Serializable
data class AuthUserRequest(
    val token: String
)
