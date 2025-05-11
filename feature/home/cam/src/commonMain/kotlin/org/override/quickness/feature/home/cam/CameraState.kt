package org.override.quickness.feature.home.cam

data class CameraState(
    val isLoading: Boolean = false,
    val isScanning: Boolean = false,
    val valueScanned: String? = null,
    val uid: String? = null,
    val loginApollo: Boolean = false,
    val scanJustCompleted: Boolean = false,
    val loginApolloSuccess: Boolean? = null, // Resultado del login: true para éxito, false para fallo, null si no se ha intentado
    val scanJustTriggered: Boolean = false // Para iniciar la animación de carga inmediatamente
)