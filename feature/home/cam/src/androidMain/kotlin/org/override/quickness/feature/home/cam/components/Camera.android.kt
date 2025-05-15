package org.override.quickness.feature.home.cam.components

import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import org.override.quickness.shared.utils.ContextProvider

@Composable
internal actual fun Camera() {

}

private fun startCamera() {
    val context = ContextProvider.getContext()!!
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({

    }, ContextCompat.getMainExecutor(context))
}