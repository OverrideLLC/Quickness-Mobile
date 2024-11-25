package org.quickness.ui.screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isWarning: Boolean = false,
    val errorMessage: String = ""
)