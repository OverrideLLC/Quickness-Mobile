package org.quickness.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.quickness.data.repository.LoginRepository
import org.quickness.utils.`object`.ValidatesData
import org.quickness.utils.`object`.ValidatesData.isPasswordValid

class LoginViewModel(
    private val authRepository: LoginRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun updateState(update: LoginState.() -> LoginState) {
        _state.value = _state.value.update()
    }

    private fun getSessionToken(): String? {
        return null
    }

    fun login(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        ValidatesData.isEmailValid(
            email = _state.value.email,
            errorMessage = { errorMessage ->
                updateState {
                    copy(
                        isError = true,
                        isWarning = true
                    )
                }
                onError(errorMessage)
                return@isEmailValid
            }
        )
        isPasswordValid(
            password = _state.value.password,
            errorMessage = { errorMessage ->
                updateState {
                    copy(
                        isError = true,
                        isWarning = true
                    )
                }
                onError(errorMessage)
                return@isPasswordValid
            }
        )

        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            try {
                val result = authRepository.login(_state.value.email, _state.value.password)
                if (result?.status == "Success") {
                    updateState { copy(isError = false, isWarning = false) }
                    onSuccess()
                } else {
                    updateState { copy(isError = true, isWarning = true) }
                    onError(result?.status ?: "Error connecting to server")
                }
            } catch (e: Exception) {
                updateState { copy(isError = true, isWarning = true) }
                onError(e.message ?: "Error connecting to server")
            } finally {
                updateState { copy(isLoading = true) }
            }
        }
    }
}