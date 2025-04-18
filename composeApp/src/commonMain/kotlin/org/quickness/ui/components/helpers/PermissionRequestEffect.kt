package org.quickness.ui.components.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory

@Composable
fun PermissionRequestEffect(
    permission: Permission,
    onPermissionResult: (Boolean) -> Unit
) {
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)

    LaunchedEffect(controller) {
        try {
            controller.providePermission(permission)
            onPermissionResult(controller.isPermissionGranted(permission))
        } catch (_: DeniedException) {
            onPermissionResult(false)
        } catch (_: DeniedAlwaysException) {
            onPermissionResult(false)
        } catch (_: RequestCanceledException) {
            onPermissionResult(false)
        }
    }
}