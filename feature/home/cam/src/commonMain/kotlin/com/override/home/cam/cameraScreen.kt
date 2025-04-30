package com.override.home.cam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shared.resources.drawable.ResourceNameKey
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
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
    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopBarCamera(
                navController = navController
            )
        },
        content = { padding ->
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
                overlayColor = colorScheme.primaryContainer.copy(0.5f),
                overlayBorderColor = colorScheme.primary,
                permissionDeniedView = {
                    CameraNoPermissions()
                }
            )
        }
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            state.valueScanned?.let { value ->
                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 30.sp,
                )
            }
        }
    )
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
internal fun TopBarCamera(
    navController: NavController,
    viewmodel: CameraViewModel = koinViewModel()
) {
    Row(
        modifier = Modifier.fillMaxWidth().background(colorScheme.onBackground),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            IconButton(
                onClick = { navController.popBackStack() },
                content = {
                    Icon(
                        painter = painterResource(viewmodel.getDrawable(ResourceNameKey.ARROW_BACK_IOS_24DP_E8EAED_FILL0_WGHT400_GRAD0_OPSZ24.name)),
                        contentDescription = "Back",
                        tint = colorScheme.primary,
                        modifier = Modifier
                    )
                }
            )
        }
    )
}