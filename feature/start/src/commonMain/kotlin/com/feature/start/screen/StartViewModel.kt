package com.feature.start.screen

import androidx.lifecycle.ViewModel
import com.example.api.repository.TokenDatabaseRepository
import com.network.api.repository.AuthRepository
import com.shared.resources.interfaces.Resources
import com.shared.resources.interfaces.ResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.jetbrains.compose.resources.DrawableResource

class StartViewModel(
    private val resources: Resources,
    private val authRepository: AuthRepository,
    private val tokenDatabaseRepository: TokenDatabaseRepository
) : ViewModel(), ResourcesProvider {
    data class StartState(
        val name: String = ""
    )

    private val _state = MutableStateFlow(StartState())
    val state = _state.asStateFlow()

    fun update(update: StartState.() -> StartState) {
        _state.value = update(_state.value)
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}