package org.quickness.interfaces.viewmodels

import kotlinx.coroutines.flow.StateFlow
import org.quickness.network.response.AuthResponse
import org.quickness.ui.states.LoginState

interface LoginInterface {
    val state: StateFlow<LoginState>
    fun update(update: LoginState.() -> LoginState)
    fun login(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    suspend fun loginSuccess(
        loginResult: AuthResponse,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    fun validateInputs(onError: (String) -> Unit): Boolean
    fun loginError(
        errorMessage: String,
        onError: (String) -> Unit
    )
}