package com.override.home.cam

sealed interface CameraActions {
    object OnCompleteScan : CameraActions
}