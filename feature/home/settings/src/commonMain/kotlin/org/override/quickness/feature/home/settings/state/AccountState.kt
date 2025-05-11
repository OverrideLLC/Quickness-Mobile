package org.override.quickness.feature.home.settings.state

/**
 * Represents the state of the Account screen.
 *
 * This data class holds all the information related to the account settings,
 * including UI state, user inputs, and error/success messages.
 *
 * @property showBottomSheetChangePassword Indicates whether to show the bottom sheet for changing the password.
 * @property showBottomSheetChangeEmail Indicates whether to show the bottom sheet for changing the email.
 * @property showBottomSheetLogout Indicates whether to show the bottom sheet for logging out.
 * @property email The user's current email address.
 * @property password The user's current password.
 * @property newEmail The new email address entered by the user.
 * @property newPassword The new password entered by the user.
 * @property confirmPassword The confirmation password entered by the user.
 * @property visibilityPassword Indicates whether the password should be visible.
 * @property isError Indicates whether an error occurred.
 * @property message The error or success message.
 * @property success Indicates whether an operation was successful.
 * @property isLoading Indicates whether a loading operation is in progress.
 */
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
