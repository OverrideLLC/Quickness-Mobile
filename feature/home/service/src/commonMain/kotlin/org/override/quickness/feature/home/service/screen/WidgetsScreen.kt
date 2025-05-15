package org.override.quickness.feature.home.service.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel
import org.override.quickness.feature.home.service.components.WidgetCard

@Composable
fun WidgetsRoot(
    viewModel: WidgetsViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WidgetsScreen(
        state = state,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@Composable
internal fun WidgetsScreen(
    state: WidgetsState,
    navController: NavController,
    onAction: (WidgetsAction) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        content = {
            items(state.widgets.size) {
                WidgetCard(
                    name = state.widgets[it].name,
                    icon = state.widgets[it].icon,
                    onClick = { navController.navigate(state.widgets[it].route) }
                )
            }
        }
    )
}