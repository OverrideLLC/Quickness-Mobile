package com.override.home.cam

data class CameraState(
    val isLoading: Boolean = false,
    val isScanning: Boolean = false,
    val valueScanned: String? = null,
    val uid: String? = null,
    val loginApollo: Boolean = false,
)