package org.override.quickness.feature.home.cam

sealed interface CameraActions {
    object OnCompleteScan : CameraActions
}