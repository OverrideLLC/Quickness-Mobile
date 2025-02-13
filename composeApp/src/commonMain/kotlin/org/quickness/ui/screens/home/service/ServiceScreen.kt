package org.quickness.ui.screens.home.service

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.services.lyra.WidgetLyra
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ServiceScreen() = Screen()

@Composable
private fun Screen(viewModel: ServiceViewModel = koinViewModel()) {
    val scope = rememberCoroutineScope()
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)

    scope.launch {
        viewModel.checkPermissions(
            permissions = Permission.COARSE_LOCATION,
            controller = controller
        )
        viewModel.checkPermissions(
            permissions = Permission.LOCATION,
            controller = controller
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item { WidgetLyra() }
    }
}