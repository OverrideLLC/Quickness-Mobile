package com.feature.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feature.login.state.LoginState
import com.network.api.repository.AuthRepository
import com.network.api.response.AuthResponse
import com.quickness.shared.utils.objects.Constants
import com.quickness.shared.utils.objects.KeysCache
import com.quickness.shared.utils.objects.ValidatesData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonPrimitive
import org.quickness.interfaces.repository.data.DataStoreRepository
import org.quickness.interfaces.viewmodels.LoginViewModelInterface
import org.quickness.ui.states.LoginState

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun update(update: LoginState.() -> LoginState) {
        _state.value = _state.value.update()
    }

    fun validateInputs(onError: (String) -> Unit): Boolean {
        var isValid = true
        ValidatesData.isEmailValid(
            email = _state.value.email,
            errorMessage = { errorMessage ->
                loginError(errorMessage, onError)
                isValid = false
            }
        )
        ValidatesData.isPasswordValid(
            password = _state.value.password,
            errorMessage = { errorMessage ->
                loginError(errorMessage, onError)
                isValid = false
            }
        )
        return isValid
    }

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (!validateInputs(onError)) return
        viewModelScope.launch {
            update { copy(isLoading = true) }
            try {
                val loginResult = authRepository.login(
                    email = _state.value.email,
                    password = _state.value.password
                )
                if (loginResult.status == Constants.SUCCESS_STATUS) {
                    loginSuccess(
                        loginResult = loginResult,
                        onSuccess = onSuccess,
                        onError = onError
                    )
                } else {
                    loginError(loginResult.message ?: "Error connecting to server", onError)
                }
            } catch (loginError: Exception) {
                loginError(loginError.message ?: "Error connecting to server", onError)
            } finally {
                update { copy(isLoading = false) }
            }
        }
    }

    suspend fun loginSuccess(
        loginResult: AuthResponse,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val jwtResult = authRepository.jwt(
                token = loginResult.jwt ?: return onError("Error obteniendo el token JWT")
            )
            if (jwtResult.status == Constants.OK_STATUS) {
                update { copy(isError = false, isWarning = false, isLoading = false) }
                dataStoreRepository.saveString(
                    buildMap {
                        put(KeysCache.JWT_KEY, jwtResult.data.getValue("jwt").jsonPrimitive.content)
                        put(KeysCache.JWT_FIREBASE_KEY, loginResult.jwt ?: "")
                    }
                )
                onSuccess()
            } else {
                loginError(errorMessage = jwtResult.message, onError = onError)
            }
        } catch (jwtError: Exception) {
            loginError(
                errorMessage = jwtError.message ?: "Error connecting to server",
                onError = onError
            )
        }
    }

    fun loginError(
        errorMessage: String,
        onError: (String) -> Unit
    ) {
        update { copy(isError = true, isWarning = true, isLoading = false) }
        onError(errorMessage)
    }
}