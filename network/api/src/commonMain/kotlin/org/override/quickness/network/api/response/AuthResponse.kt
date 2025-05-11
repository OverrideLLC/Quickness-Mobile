<<<<<<<< HEAD:network/api/src/commonMain/kotlin/org/override/quickness/network/api/response/AuthResponse.kt
package org.override.quickness.network.api.response
========
package org.quickness.data.response
>>>>>>>> origin/master:shared/utils/src/commonMain/kotlin/org/override/quickness/shared/data/response/AuthResponse.kt

/**
 * Representa el resultado de un proceso de autenticación.
 *
 * @property status Indica el estado del resultado de autenticación. Puede ser un valor como "success" o "error".
 * @property uid Identificador único del usuario autenticado. Es opcional y puede ser `null` si la autenticación falla.
 * @property message Mensaje adicional relacionado con el resultado de la autenticación. Puede proporcionar detalles sobre errores o confirmaciones. Es opcional.
 */
data class AuthResponse(
    val status: String, // Estado de la autenticación (por ejemplo, éxito o error).
    val uid: String? = null, // UID del usuario si la autenticación fue exitosa; null en caso contrario.
    val jwt: String? = null,
    val message: String? = null // Mensaje opcional con información adicional sobre el resultado.
)
