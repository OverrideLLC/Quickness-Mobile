package org.override.quickness.feature.home.cam.analyzer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.home.cam.components.Camera

@Composable
fun CamAnalyzerRoot(
    viewModel: CamAnalyzerViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CamAnalyzerScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun CamAnalyzerScreen(
    state: CamAnalyzerState,
    onAction: (CamAnalyzerAction) -> Unit,
) {
    Camera()
}