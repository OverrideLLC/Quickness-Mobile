package org.quickness.ui.states

/**
 * Represents the state of the Forgot Password screen.
 *
 * This data class holds information about the current state of the forgot password process,
 * including the email address entered, loading status, success status, and error status.
 *
 * @property email The email address entered by the user. Defaults to an empty string.
 * @property isLoading Indicates whether a forgot password request is currently in progress. Defaults to false.
 * @property success Indicates whether the forgot password request was successful. Defaults to false.
 * @property error Indicates whether an error occurred during the forgot password request. Defaults to false.
 */
data class ForgotPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: Boolean = false
)
