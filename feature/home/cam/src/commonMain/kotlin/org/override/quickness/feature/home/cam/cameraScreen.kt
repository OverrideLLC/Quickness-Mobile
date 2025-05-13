package org.override.quickness.feature.home.cam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.shared.resources.drawable.ResourceNameKey
import org.override.quickness.shared.ui.styles.TextStyleBrush
import qrscanner.CameraLens
import qrscanner.OverlayShape
import qrscanner.QrScanner

@Composable
fun CameraRoot(
    viewModel: CameraViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CameraScreen(
        state = state,
        viewModel = viewModel,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@Composable
fun CameraScreen(
    state: CameraState,
    viewModel: CameraViewModel,
    onAction: (CameraActions) -> Unit,
    navController: NavController,
) {
    if (!state.isLoading) {
        QrScanner(
            modifier = Modifier.fillMaxSize(),
            flashlightOn = false,
            cameraLens = CameraLens.Back,
            openImagePicker = false,
            onCompletion = { value ->
                viewModel.update { copy(valueScanned = value) }
                onAction(CameraActions.OnCompleteScan)
            },
            imagePickerHandler = { },
            onFailure = { },
            overlayShape = OverlayShape.Square,
            overlayColor = Color.Transparent,
            overlayBorderColor = colorScheme.primary,
            permissionDeniedView = {
                CameraNoPermissions()
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent)
                        .align(
                            alignment = Alignment.TopStart
                        ),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { navController.popBackStack() },
                        content = {
                            Icon(
                                painter = painterResource(viewModel.getDrawable(ResourceNameKey.ARROW_BACK_IOS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = colorScheme.primary
                            )
                        }
                    )
                    Text(
                        text = "Camera",
                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Start,
                        style = TextStyleBrush()
                    )
                }
            }
        )
    } else {
        QrScannerLoadingScreen()
    }
}

@Composable
fun CameraNoPermissions() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            Text(
                text = "Camera permissions not granted",
                color = Color.White,
                fontSize = 30.sp,
            )
        }
    )
}

@Composable
fun QrScannerLoadingScreen(
    modifier: Modifier = Modifier,
    loadingMessage: String = "Procesando QR...\nPor favor, espera.",
    progressIndicatorColor: Color = colorScheme.primary,
    textColor: Color = colorScheme.onSurface
) {
    // Contenedor principal que ocupa toda la pantalla y centra su contenido.
    Surface(
        modifier = modifier.fillMaxSize(),
        color = colorScheme.background.copy(alpha = 0.8f) // Fondo semi-transparente opcional
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Indicador de progreso circular
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = progressIndicatorColor,
                strokeWidth = 5.dp
            )

            Spacer(modifier = Modifier.height(24.dp)) // Espacio entre el indicador y el texto

            // Mensaje de carga
            Text(
                text = loadingMessage,
                fontSize = 18.sp,
                color = textColor,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}