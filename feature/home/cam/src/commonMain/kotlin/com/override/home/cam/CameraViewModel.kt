package com.override.home.cam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.repository.DataStoreRepository
import com.network.api.repository.ApolloRepository
import com.network.api.request.ApolloRequestQr
import com.quickness.shared.utils.objects.KeysCache
import com.shared.resources.interfaces.Resources
import com.shared.resources.interfaces.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource

class CameraViewModel(
    private val resources: Resources,
    private val apolloRepository: ApolloRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel(), ResourcesProvider {

    private val _hasLoadedInitialData = MutableStateFlow(false)

    private val _state = MutableStateFlow(CameraState())
    val state = _state
        .onStart {
            _hasLoadedInitialData.update { it.not() }
            val uid = dataStoreRepository.getString(
                key = KeysCache.UID,
                defaultValue = ""
            )

            _state.update {
                it.copy(
                    uid = uid
                )
            }
            _hasLoadedInitialData.update { it.not() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CameraState()
        )

    fun onAction(action: CameraActions) {
        when (action) {
            is CameraActions.OnCompleteScan -> {
                decrypt()
            }
        }
    }

    fun update(update: CameraState.() -> CameraState) {
        _state.value = _state.value.update()
    }

    private fun decrypt(token: String? = null) {
        update { copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                println("token: $token")
                apolloRepository.login(
                    ApolloRequestQr(
                        uid = state.value.uid ?: "",
                        token = _state.value.valueScanned ?: ""
                    )
                )
            }.onSuccess { apiResponse ->
                when (apiResponse.status) {
                    200 -> _state.update { it.copy(loginApollo = true) }
                    400 -> _state.update { it.copy(loginApollo = false) }
                }
                println("apiResponse: $apiResponse")
            }.onFailure {
                _state.update { it.copy(loginApollo = false) }
                println("Error: ${it.message}")
            }
        }.invokeOnCompletion {
            update { copy(isLoading = false) }
        }
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}