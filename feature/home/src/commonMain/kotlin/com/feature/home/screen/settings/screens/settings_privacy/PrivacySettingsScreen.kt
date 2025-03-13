package com.feature.home.screen.settings.screens.settings_privacy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.quickness.ui.components.component.BottomSheetContent
import org.quickness.ui.components.component.SettingsItem
import org.quickness.ui.states.PrivacySettingsState
import quickness.composeapp.generated.resources.Res
import quickness.composeapp.generated.resources.data_download
import quickness.composeapp.generated.resources.download_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24

@Composable
fun PrivacySettingsScreen() = Screen()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(viewModel: PrivacySettingsViewModel = koinViewModel()) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState().value

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        item {
            SettingsItem(
                name = Res.string.data_download,
                icon = Res.drawable.download_24dp_E8EAED_FILL0_wght400_GRAD0_opsz24,
                navigator = {
                    scope.launch {
                        viewModel.update { copy(showBottomSheetDownload = true) }
                        sheetState.show()
                    }
                }
            )
        }
    }
    bottomSheet(sheetState, scope, viewModel, state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    viewModel: PrivacySettingsViewModel,
    state: PrivacySettingsState
) {
    BottomSheetContent(
        sheetState = sheetState,
        colorBackground = colorScheme.onBackground.copy(alpha = 0.5f),
        showContent = state.showBottomSheetDownload,
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(showBottomSheetDownload = false) }
            }
        },
        content = {
            DownloadData {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) viewModel.update { copy(showBottomSheetDownload = false) }
                }
            }
        }
    )
}

@Composable
fun DownloadData(
    onDownload: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 16.dp)
    ) {
        Button(
            onClick = onDownload,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.tertiary,
                contentColor = colorScheme.background
            ),
            content = { Text(text = "Download Data") }
        )
    }
}