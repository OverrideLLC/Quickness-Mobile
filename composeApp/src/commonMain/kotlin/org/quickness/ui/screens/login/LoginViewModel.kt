package org.quickness.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quickness.data.repository.AuthRepository

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isWarning = MutableStateFlow(false)
    val isWarning: StateFlow<Boolean> = _isWarning

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _isError.value = false
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _isError.value = false
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun toggleError() {
        _isError.value = !_isError.value
    }

    fun isWarning(value: Boolean) {
        _isWarning.value = value
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$") // Patrón general para emails
        return emailRegex.matches(email)
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8 && // Mínima longitud
                password.any { it.isUpperCase() } && // Al menos una mayúscula
                password.any { it.isLowerCase() } && // Al menos una minúscula
                password.any { it.isDigit() } && // Al menos un número
                password.any { "!@#\$%^&*()-_=+[{]}|;:'\",<.>/?".contains(it) } // Al menos un carácter especial
    }

    private fun areFieldsNotEmpty(vararg fields: String): Boolean {
        return fields.all { it.isNotBlank() }
    }

    private fun isPasswordSecure(email: String, password: String): Boolean {
        return email != password
    }

    private fun isUserAlreadyLoggedIn(): Boolean {
        // Esto puede depender de tu sistema, por ejemplo, validar si hay un token activo guardado localmente
        val activeSessionToken = getSessionToken()
        return !activeSessionToken.isNullOrBlank()
    }

    private fun getSessionToken(): String? {
        // Implementación que lee el token de un almacenamiento seguro
        return null // Aquí va tu lógica para obtener el token, si existe
    }

    fun login(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _isError.value = true
            onError("Email and password cannot be empty")
            isWarning(true)
            return
        }else if (!isEmailValid(_email.value)) {
            _isError.value = true
            onError("Invalid email format")
            isWarning(true)
            return
        }else if (!isPasswordValid(_password.value)) {
            _isError.value = true
            onError("Invalid password format")
            isWarning(true)
            return
        }else if (!areFieldsNotEmpty(_email.value, _password.value)) {
            _isError.value = true
            isWarning(true)
            onError("Email and password cannot be empty")
            return
        }else if (!isPasswordSecure(_email.value, _password.value)) {
            _isError.value = true
            onError("Email and password cannot be the same")
            isWarning(true)
            return
        }else if (isUserAlreadyLoggedIn()) {
            _isError.value = true
            onError("User already logged in")
            isWarning(true)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = authRepository.login(_email.value, _password.value)
                if (result.isSuccessful) {
                    onSuccess()
                } else {
                    _isError.value = true
                    isWarning(true)
                    onError(result.errorMessage ?: "Unknown error")
                }
            } catch (e: Exception) {
                _isError.value = true
                isWarning(true)
                onError(e.message ?: "Error connecting to server")
            } finally {
                _isLoading.value = false
            }
        }
    }
}