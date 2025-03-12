package com.feature.home.service.screen

import androidx.lifecycle.ViewModel
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ServiceViewModel : ViewModel() {
    data class ServiceState(
        val service: String = ""
    )

    private val _state = MutableStateFlow(ServiceState())
    val state = _state.asStateFlow()

    fun update(service: String) {
        _state.value = _state.value.copy(service = service)
    }

    suspend fun checkPermissions(
        permissions: Permission,
        controller: PermissionsController,
    ) {
        val granted = controller.isPermissionGranted(permissions)
        if (!granted) {
            try {
                controller.providePermission(permissions)
            } catch (_: Exception) {
                println("Permission denied")
            }
        }
    }
}