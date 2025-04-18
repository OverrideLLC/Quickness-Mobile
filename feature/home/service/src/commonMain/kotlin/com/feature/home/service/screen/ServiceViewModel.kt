package com.feature.home.service.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.feature.home.service.states.ServiceState
import com.shared.resources.interfaces.Resources
import com.shared.resources.interfaces.ResourcesProvider
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource

class ServiceViewModel(
    private val resources: Resources
) : ViewModel(), ResourcesProvider {
    private val _state = MutableStateFlow(ServiceState())
    val state = _state.asStateFlow()

    fun update(update: ServiceState.() -> ServiceState) {
        _state.value = update(_state.value)
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

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}