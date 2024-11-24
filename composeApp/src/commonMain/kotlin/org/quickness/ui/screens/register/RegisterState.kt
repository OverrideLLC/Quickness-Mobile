package org.quickness.ui.screens.register

/**
 * Data class representing the state of the registration process.
 * This class holds all the necessary data and flags used during user registration.
 *
 * @property email The email address entered by the user.
 * @property password The password entered by the user.
 * @property confirmPassword The confirmation of the password entered by the user.
 * @property name The full name of the user.
 * @property curp The CURP (Clave Única de Registro de Población) of the user, used in some regions for identification.
 * @property phoneNumber The phone number provided by the user.
 * @property termsAndConditions A flag indicating if the user has accepted the terms and conditions.
 * @property privacyPolicy A flag indicating if the user has accepted the privacy policy.
 * @property dataAnalytics A flag indicating if the user has agreed to data analytics usage.
 * @property day The day of the user's birth date.
 * @property month The month of the user's birth date.
 * @property year The year of the user's birth date.
 * @property sex The sex of the user (e.g., "Male", "Female", "Other").
 * @property selectedState The state or region selected by the user.
 * @property verificationCode The verification code entered by the user, typically used for confirming their email or phone number.
 * @property isVisiblePassword A flag indicating if the password field should be visible (not obscured).
 * @property isVisibleConfirmPassword A flag indicating if the confirm password field should be visible (not obscured).
 * @property isError A flag indicating if there is an error in the registration process.
 * @property errorMessage A descriptive message providing details about the current error, if any.
 * @property isLoading A flag indicating if a loading process is ongoing (e.g., submitting data to a server).
 */
data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val curp: String = "",
    val phoneNumber: String = "",
    val termsAndConditions: Boolean = false,
    val privacyPolicy: Boolean = false,
    val dataAnalytics: Boolean = false,
    val day: String = "",
    val month: String = "",
    val year: String = "",
    val sex: String = "",
    val selectedState: String = "",
    val verificationCode: String = "",
    val isVisiblePassword: Boolean = false,
    val isVisibleConfirmPassword: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
)