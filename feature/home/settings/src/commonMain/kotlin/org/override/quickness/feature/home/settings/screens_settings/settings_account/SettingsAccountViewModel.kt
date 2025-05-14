package org.override.quickness.feature.home.settings.screens_settings.settings_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.override.quickness.data.api.repository.DataStoreRepository
import org.override.quickness.data.api.repository.TokenDatabaseRepository
import org.override.quickness.network.api.repository.TokensRepository
import org.override.quickness.shared.resources.interfaces.Resources
import org.override.quickness.shared.resources.interfaces.ResourcesProvider
import org.override.quickness.shared.utils.objects.KeysCache.UID

class SettingsAccountViewModel(
    private val resources: Resources,
    private val dataStoreRepository: DataStoreRepository,
    private val tokenDatabaseRepository: TokenDatabaseRepository
) : ViewModel(), ResourcesProvider {

    private val _state = MutableStateFlow(SettingsAccountState())
    val state = _state
        .onStart {
            loadData()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsAccountState()
        )

    fun onAction(action: SettingsAccountAction) {
        when (action) {
            is SettingsAccountAction.OnOpen -> {
                _state.update { currentState ->
                    currentState.copy(
                        onOpen = !currentState.onOpen
                    )
                }
            }

            is SettingsAccountAction.Logout -> {
                viewModelScope.launch(Dispatchers.IO) {
                    runCatching {
                        dataStoreRepository.clear()
                    }.onSuccess {
                        tokenDatabaseRepository.clearTokens()
                        _state.update { currentState ->
                            currentState.copy(
                                dialogOpen = false
                            )
                        }
                    }.onFailure {
                        it.printStackTrace()
                        _state.update { currentState ->
                            currentState.copy(
                                dialogOpen = false
                            )
                        }
                    }
                }
            }

            is SettingsAccountAction.OnCloseDialog -> {
                _state.update { currentState ->
                    currentState.copy(
                        dialogOpen = false
                    )
                }
            }

            is SettingsAccountAction.OnOpenDialog -> {
                _state.update { currentState ->
                    currentState.copy(
                        dialogOpen = true
                    )
                }
            }
        }
    }

    private fun loadData() {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                dataStoreRepository.getString(
                    UID,
                    ""
                )
            }.onSuccess { uid ->
                println("UID: $uid")
                _state.update { currentState ->
                    currentState.copy(
                        uid = uid!!,
                        isLoading = false
                    )
                }
            }.onFailure {
                it.printStackTrace()
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}