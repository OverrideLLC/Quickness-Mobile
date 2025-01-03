package org.quickness.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonPrimitive
import org.quickness.SharedPreference
import org.quickness.data.repository.AuthRepositoryImpl
import org.quickness.interfaces.viewmodels.LoginInterface
import org.quickness.ui.states.LoginState
import org.quickness.utils.`object`.KeysCache.JWT_FIREBASE_KEY
import org.quickness.utils.`object`.KeysCache.JWT_KEY
import org.quickness.utils.`object`.KeysCache.UID_KEY
import org.quickness.utils.`object`.ValidatesData
import org.quickness.utils.`object`.ValidatesData.isPasswordValid

class LoginViewModel(
    private val authRepository: AuthRepositoryImpl,
    private val sharedPreference: SharedPreference,
) : ViewModel(), LoginInterface {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun updateState(update: LoginState.() -> LoginState) {
        _state.value = _state.value.update()
    }

    override fun login(
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
            runCatching {
                authRepository.login(_state.value.email, _state.value.password)
            }.onSuccess { loginResult ->
                if (loginResult.status == "Success")
                    runCatching {
                        println("JWT: ${loginResult.jwt}")
                        authRepository.jwt(loginResult.jwt!!)
                    }.onSuccess { jwtResult ->
                        if (jwtResult.status == 200) {
                            onSuccess()
                            updateState { copy(isError = false, isWarning = false) }
                            sharedPreference.setString(UID_KEY, loginResult.uid ?: "")
                            sharedPreference.setMap(
                                JWT_KEY,
                                jwtResult.data.mapValues { it.value.jsonPrimitive.content }
                            )
                            sharedPreference.setString(
                                JWT_FIREBASE_KEY,
                                loginResult.jwt ?: ""
                            )
                        } else
                            onError(jwtResult.message)
                    }.onFailure { jwtError ->
                        onError(jwtError.message ?: "Error obteniendo el token JWT")
                        updateState { copy(isError = true, isWarning = true) }
                    }
                else {
                    updateState { copy(isError = true, isWarning = true) }
                    onError(loginResult.status)
                }
            }.onFailure { loginError ->
                updateState { copy(isError = true, isWarning = true) }
                onError(loginError.message ?: "Error connecting to server")
            }.also {
                updateState { copy(isLoading = false) }
            }
        }
    }
}