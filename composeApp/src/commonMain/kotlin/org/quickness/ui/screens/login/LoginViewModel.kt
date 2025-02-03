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
import org.quickness.ui.states.LoginState
import org.quickness.utils.objects.KeysCache.JWT_FIREBASE_KEY
import org.quickness.utils.objects.KeysCache.JWT_KEY
import org.quickness.utils.objects.ValidatesData
import org.quickness.utils.objects.ValidatesData.isPasswordValid

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel(), LoginInterface {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun update(update: LoginState.() -> LoginState) {
        _state.value = _state.value.update()
    }

    private fun validateInputs(onError: (String) -> Unit) {
        ValidatesData.isEmailValid(
            email = _state.value.email,
            errorMessage = { errorMessage ->
                update { copy(isError = true, isWarning = true) }
                onError(errorMessage)
            }
        )
        isPasswordValid(
            password = _state.value.password,
            errorMessage = { errorMessage ->
                update { copy(isError = true, isWarning = true) }
                onError(errorMessage)
            }
        )
    }

    override fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        validateInputs { errorMessage ->
            onError(errorMessage)
            update { copy(isError = true, isWarning = true, isLoading = false) }
            return@validateInputs
        }
        viewModelScope.launch {
            update { copy(isLoading = true) }
            runCatching {
                authRepository.login(email = _state.value.email, password = _state.value.password)
            }.onSuccess { loginResult ->
                if (loginResult.status == "Success")
                    runCatching {
                        authRepository.jwt(token = loginResult.jwt!!)
                    }.onSuccess { jwtResult ->
                        if (jwtResult.status == 200) {
                            onSuccess()
                            update { copy(isError = false, isWarning = false) }
                            println(jwtResult.data.getValue("jwt"))
                            dataStoreRepository.saveString(
                                mapOf(
                                    JWT_KEY to jwtResult.data.getValue("jwt").jsonPrimitive.content,
                                    JWT_FIREBASE_KEY to loginResult.jwt!!
                                )
                            )
                        } else {
                            onError(jwtResult.message)
                        }
                    }.onFailure { jwtError ->
                        onError(jwtError.message ?: "Error obteniendo el token JWT")
                        update { copy(isError = true, isWarning = true) }
                    }
                else {
                    update { copy(isError = true, isWarning = true) }
                    onError(loginResult.status)
                }
            }.onFailure { loginError ->
                update { copy(isError = true, isWarning = true) }
                onError(loginError.message ?: "Error connecting to server")
            }.also {
                update { copy(isLoading = false) }
            }
        }
    }
}