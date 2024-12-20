package org.quickness.ui.screens.home.settings.screens.settings_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.quickness.data.service.FirebaseService
import org.quickness.utils.`object`.ValidatesData

class AccountSettingsViewModel(
    private val firebaseService: FirebaseService
) : ViewModel() {
    data class AccountState(
        val showBottomSheetChangePassword: Boolean = false,
        val showBottomSheetChangeEmail: Boolean = false,
        val showBottomSheetLogout: Boolean = false,
        val email: String = "",
        val password: String = "",
        val newEmail: String = "",
        val newPassword: String = "",
        val confirmPassword: String = "",
        val visibilityPassword: Boolean = false,
        val isError: Boolean = false,
        val message: String = "",
        val success: Boolean = false,
        val isLoading: Boolean = false
    )

    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    fun update(newState: AccountState.() -> AccountState) {
        _state.value = _state.value.newState()
    }

    fun changePassword() {
        if (_state.value.email.isEmpty() || _state.value.password.isEmpty() || _state.value.newPassword.isEmpty()) {
            update { copy(isError = true, message = "Please fill all fields") }
            return
        }
        ValidatesData.isPasswordValid(
            password = _state.value.password,
            errorMessage = {
                update { copy(isError = true, message = it) }
                return@isPasswordValid
            }
        )
        ValidatesData.isEmailValid(
            email = _state.value.email,
            errorMessage = {
                update { copy(isError = true, message = it) }
                return@isEmailValid
            }
        )
        if (_state.value.newPassword == _state.value.password) {
            update {
                copy(
                    isError = true,
                    message = "The new password cannot be the same as the old one"
                )
            }
            return
        }
        ValidatesData.confirmPassword(
            password = _state.value.newPassword,
            confirmPassword = _state.value.confirmPassword,
            errorMessage = {
                update {
                    copy(
                        isError = true,
                        message = it
                    )
                }
                return@confirmPassword
            }
        )
        viewModelScope.launch {
            update { copy(showBottomSheetChangePassword = false, isLoading = true) }
            firebaseService.reauthenticateAndChangePassword(
                email = _state.value.email,
                currentPassword = _state.value.password,
                newPassword = _state.value.newPassword,
                onSuccess = {
                    update { copy(success = true, isLoading = false) }
                },
                onError = {
                    update { copy(isError = true, message = "Error", isLoading = false) }
                }
            )
        }
    }

    fun changeEmail() {
        if (_state.value.newEmail.isEmpty() || _state.value.password.isEmpty() || _state.value.email.isEmpty()) {
            update { copy(isError = true, message = "Please fill all fields") }
            return
        }
        ValidatesData.isPasswordValid(
            password = _state.value.password,
            errorMessage = {
                update { copy(isError = true, message = it) }
                return@isPasswordValid
            }
        )
        ValidatesData.isEmailValid(
            email = _state.value.newEmail,
            errorMessage = {
                update { copy(isError = true, message = it) }
                return@isEmailValid
            }
        )
        ValidatesData.isEmailValid(
            email = _state.value.newEmail,
            errorMessage = {
                update { copy(isError = true, message = it) }
                return@isEmailValid
            }
        )

        viewModelScope.launch {
            update { copy(showBottomSheetChangeEmail = false, isLoading = true) }
            firebaseService.reauthenticate(
                email = _state.value.email,
                currentPassword = _state.value.password,
                onSuccess = {
                    firebaseService.changeEmail(
                        newEmail = _state.value.newEmail,
                        onSuccess = {
                            update {
                                copy(
                                    success = true,
                                    isLoading = false,
                                    message = "Verify your email"
                                )
                            }
                        },
                        onError = {
                            update {
                                copy(
                                    isError = true,
                                    message = "Error ${it.message}",
                                    isLoading = false
                                )
                            }
                        }
                    )
                },
                onError = {
                    update {
                        copy(
                            isError = true,
                            message = "Error ${it.message}",
                            isLoading = false
                        )
                    }
                }
            )
        }
    }

    fun logOut() {
        viewModelScope.launch {
            firebaseService.logOut(
                onSuccess = {
                    update { copy(showBottomSheetLogout = false) }
                },
                onError = {
                    update { copy(isError = true, message = "Error ${it.message}") }
                }
            )
        }
    }
}