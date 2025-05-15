package org.override.quickness.feature.home.cam.scanner

sealed interface CameraActions {
    object OnCompleteScan : CameraActions
}