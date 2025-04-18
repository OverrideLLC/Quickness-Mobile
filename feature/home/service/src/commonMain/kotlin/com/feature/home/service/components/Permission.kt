package com.feature.home.service.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.feature.home.service.screen.ServiceViewModel
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch

@Composable
internal fun Permission(viewModel: ServiceViewModel) {
    val scope = rememberCoroutineScope()
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)

    scope.launch {
        viewModel.checkPermissions(
            permissions = dev.icerock.moko.permissions.Permission.COARSE_LOCATION,
            controller = controller
        )
        viewModel.checkPermissions(
            permissions = dev.icerock.moko.permissions.Permission.LOCATION,
            controller = controller
        )
    }
}
