package org.quickness.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonPrimitive
import org.quickness.interfaces.repository.data.DataStoreRepository
import org.quickness.interfaces.repository.network.AuthRepository
import org.quickness.interfaces.viewmodels.LoginInterface
import org.quickness.network.response.AuthResponse
import org.quickness.ui.states.LoginState
import org.quickness.utils.objects.Constants.OK_STATUS
import org.quickness.utils.objects.Constants.SUCCESS_STATUS
import org.quickness.utils.objects.KeysCache.JWT_FIREBASE_KEY
import org.quickness.utils.objects.KeysCache.JWT_KEY
import org.quickness.utils.objects.ValidatesData
import org.quickness.utils.objects.ValidatesData.isPasswordValid

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel(), LoginInterface {
    private val _state = MutableStateFlow(LoginState())
    override val state: StateFlow<LoginState> = _state.asStateFlow()

    override fun update(update: LoginState.() -> LoginState) {
        _state.value = _state.value.update()
    }

    override fun validateInputs(onError: (String) -> Unit): Boolean {
        var isValid = true
        ValidatesData.isEmailValid(
            email = _state.value.email,
            errorMessage = { errorMessage ->
                loginError(errorMessage, onError)
                isValid = false
            }
        )
        isPasswordValid(
            password = _state.value.password,
            errorMessage = { errorMessage ->
                loginError(errorMessage, onError)
                isValid = false
            }
        )
        return isValid
    }

    override fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (!validateInputs(onError)) return
        viewModelScope.launch {
            update { copy(isLoading = true) }
            try {
                val loginResult = authRepository.login(
                    email = _state.value.email,
                    password = _state.value.password
                )
                if (loginResult.status == SUCCESS_STATUS) {
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

    override suspend fun loginSuccess(
        loginResult: AuthResponse,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val jwtResult = authRepository.jwt(
                token = loginResult.jwt ?: return onError("Error obteniendo el token JWT")
            )
            if (jwtResult.status == OK_STATUS) {
                update { copy(isError = false, isWarning = false, isLoading = false) }
                dataStoreRepository.saveString(
                    mapOf(
                        JWT_KEY to jwtResult.data.getValue("jwt").jsonPrimitive.content,
                        JWT_FIREBASE_KEY to loginResult.jwt
                    )
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

    override fun loginError(
        errorMessage: String,
        onError: (String) -> Unit
    ) {
        update { copy(isError = true, isWarning = true, isLoading = false) }
        onError(errorMessage)
    }
}