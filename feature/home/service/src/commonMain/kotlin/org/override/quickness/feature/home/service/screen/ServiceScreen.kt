package org.override.quickness.feature.home.service.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.override.quickness.feature.home.service.components.Permission
import org.override.quickness.feature.home.service.components.WidgetService
import org.override.quickness.feature.home.service.states.ServiceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.shared.ui.component.BottomSheetContent
import org.override.quickness.shared.ui.component.Progress

@Composable
fun ServiceScreen(paddingValues: PaddingValues) = Screen(paddingValues = paddingValues)

@Composable
internal fun Screen(viewModel: ServiceViewModel = koinViewModel(), paddingValues: PaddingValues) {
    val state by viewModel.state.collectAsState()
    Permission(viewModel)
    Content(
        viewModel = viewModel,
        paddingValues = paddingValues,
        state = state,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomScreen(
    state: ServiceState,
    scope: CoroutineScope,
    viewModel: ServiceViewModel,
    sheetState: SheetState,
    content: @Composable () -> Unit,
) {
    BottomSheetContent(
        sheetState = sheetState,
        showContent = state.showBottomSheet,
        colorBackground = colorScheme.background.copy(alpha = 0.5f),
        onDismiss = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) viewModel.update { copy(showBottomSheet = false) }
            }
        },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Content(
    viewModel: ServiceViewModel,
    paddingValues: PaddingValues,
    state: ServiceState,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(state.serviceList) {
            WidgetService(
                image = it.image,
                titleService = it.titleService,
                color = it.color,
                viewModel = viewModel,
                onEvent = {
                    viewModel.update { copy(showBottomSheet = true, serviceSelected = it) }
                    scope.launch { sheetState.show() }
                }
            )
        }
    }
    BottomScreen(
        state = state,
        scope = scope,
        viewModel = viewModel,
        sheetState = sheetState,
        content = {
            state.serviceSelected?.content?.let { it() } ?: run {
                Progress(true)
            }
        }
    )
}