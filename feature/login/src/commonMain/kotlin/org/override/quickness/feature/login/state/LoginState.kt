package org.override.quickness.feature.login.state

/**
 * Data class representing the state of the login screen.
 *
 * This class holds information about the current state of the login process, including
 * user inputs, loading status, and error/warning states.
 *
 * @property email The email entered by the user. Defaults to an empty string.
 * @property password The password entered by the user. Defaults to an empty string.
 * @property isPasswordVisible Indicates whether the password is visible or hidden. Defaults to false.
 * @property isError Indicates whether an error occurred during login. Defaults to false.
 * @property isLoading Indicates whether the login process is currently in progress. Defaults to false.
 * @property isWarning Indicates whether a warning should be displayed. Defaults to false.
 * @property errorMessage The error message to be displayed if an error occurred. Defaults to an empty string.
 */
data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isWarning: Boolean = false,
    val errorMessage: String = ""
)