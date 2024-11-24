package org.quickness.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.quickness.data.repository.RegisterRepository
import org.quickness.encrypt.EncryptPasswordSHA256.encryptPasswordSHA256
import org.quickness.utils.`object`.ValidatesData.doNamesMatchCurp
import org.quickness.utils.`object`.ValidatesData.doesDateMatchCurp
import org.quickness.utils.`object`.ValidatesData.doesSexMatchCurp
import org.quickness.utils.`object`.ValidatesData.doesStateMatchCurp
import org.quickness.utils.`object`.ValidatesData.isNotInappropriate

/**
 * ViewModel responsible for managing the state and logic of the registration process.
 * Handles user input validation, state updates, and interaction with the [RegisterRepository].
 *
 * @property registerRepository The repository used for handling registration-related operations, such as submitting user data.
 */
class RegisterViewModel(
    private val registerRepository: RegisterRepository,
) : ViewModel() {

    /**
     * Holds the current state of the registration process as a [RegisterState].
     */
    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())

    /**
     * Exposes the state as an immutable [StateFlow].
     */
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    /**
     * Updates the current registration state using a transformation function.
     *
     * @param update A lambda that takes the current [RegisterState] and returns an updated version.
     */
    fun updateState(update: RegisterState.() -> RegisterState) {
        _state.value = _state.value.update()
    }

    /**
     * Validates the email and password fields.
     *
     * @return `true` if the email and password fields are valid; `false` otherwise.
     */
    fun validateEmailAndPassword(): Boolean =
        isEmailValid() && isPasswordValid() && confirmPassword()

    /**
     * Validates personal information fields such as name, CURP, and phone number.
     *
     * @return `true` if all personal information fields are valid; `false` otherwise.
     */
    fun validatePersonalInfo(): Boolean = isNameValid() && isCurpValid() && isPhoneNumberValid()

    /**
     * Validates the email format and checks if the email field is not empty.
     *
     * @return `true` if the email is valid; `false` otherwise.
     */
    private fun isEmailValid(): Boolean = when {
        _state.value.email.isEmpty() -> {
            updateState { copy(errorMessage = "Email is required") }
            false
        }

        !Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$").matches(_state.value.email) -> {
            updateState { copy(errorMessage = "Invalid email format. Please provide a valid email.") }
            false
        }

        else -> true
    }

    /**
     * Validates the password based on multiple criteria:
     * - Minimum 8 characters.
     * - At least one uppercase letter.
     * - At least one lowercase letter.
     * - At least one number.
     * - At least one special character.
     *
     * @return `true` if the password meets all criteria; `false` otherwise.
     */
    private fun isPasswordValid(): Boolean {
        val password = _state.value.password
        return when {
            password.isEmpty() -> {
                updateState { copy(errorMessage = "Password is required") }
                false
            }

            password.length < 8 -> {
                updateState { copy(errorMessage = "Password must be at least 8 characters long.") }
                false
            }

            !password.any { it.isUpperCase() } -> {
                updateState { copy(errorMessage = "Password must contain at least one uppercase letter.") }
                false
            }

            !password.any { it.isLowerCase() } -> {
                updateState { copy(errorMessage = "Password must contain at least one lowercase letter.") }
                false
            }

            !password.any { it.isDigit() } -> {
                updateState { copy(errorMessage = "Password must contain at least one number.") }
                false
            }

            !password.any { "!@#\$%^&*()-_=+[{]}|;:'\",<.>/?".contains(it) } -> {
                updateState { copy(errorMessage = "Password must contain at least one special character.") }
                false
            }

            else -> true
        }
    }

    /**
     * Confirms that the password and confirm password fields match and are not empty.
     *
     * @return `true` if the passwords match; `false` otherwise.
     */
    private fun confirmPassword(): Boolean = when {
        _state.value.confirmPassword.isEmpty() -> {
            updateState { copy(errorMessage = "Please confirm your password.") }
            false
        }

        _state.value.password != _state.value.confirmPassword -> {
            updateState { copy(errorMessage = "Passwords do not match.") }
            false
        }

        else -> true
    }

    /**
     * Validates the user's name to ensure it is not empty, has a minimum length,
     * does not contain numbers, and is not inappropriate.
     *
     * @return `true` if the name is valid; `false` otherwise.
     */
    private fun isNameValid(): Boolean = when {
        _state.value.name.isEmpty() -> {
            updateState { copy(errorMessage = "Name is required.") }
            false
        }

        _state.value.name.length < 2 -> {
            updateState { copy(errorMessage = "Name must be at least 2 characters long.") }
            false
        }

        _state.value.name.any { it.isDigit() } -> {
            updateState { copy(errorMessage = "Name cannot contain numbers.") }
            false
        }

        !isNotInappropriate(_state.value.name) -> {
            updateState { copy(errorMessage = "Invalid inappropriate name") }
            false
        }

        else -> {
            capitalizeWords()
            true
        }
    }

    /**
     * Validates the CURP field for correct format and logical consistency with other data
     * such as date of birth, sex, and selected state.
     *
     * @return `true` if the CURP is valid; `false` otherwise.
     */
    private fun isCurpValid(): Boolean {
        val curp = _state.value.curp.uppercase()
        return when {
            curp.isEmpty() -> {
                updateState { copy(errorMessage = "CURP is required.") }
                false
            }

            !Regex("^[A-Z]{4}[0-9]{6}[HM][A-Z]{2}[A-Z]{3}[0-9A-Z]{2}$").matches(curp) -> {
                updateState { copy(errorMessage = "Invalid CURP format. Please provide a valid CURP.") }
                false
            }

            !doesDateMatchCurp(
                curpDate = curp.substring(4, 10),
                day = _state.value.day,
                month = _state.value.month,
                year = _state.value.year
            ) -> {
                updateState { copy(errorMessage = "Invalid CURP format. Please provide a valid CURP.") }
                false
            }

            !doesSexMatchCurp(
                curpSex = curp[10],
                curp = curp,
                sex = _state.value.sex
            ) -> {
                updateState { copy(errorMessage = "Invalid CURP format. Please provide a valid CURP.") }
                false
            }

            !doesStateMatchCurp(
                curpState = curp.substring(11, 13),
                selectedState = _state.value.selectedState,
            ) -> {
                updateState { copy(errorMessage = "Invalid CURP format. Please provide a valid CURP.") }
                false
            }

            !doNamesMatchCurp(
                curpName = curp.substring(0, 4),
                name = _state.value.name
            ) -> {
                updateState { copy(errorMessage = "Name incorrect") }
                false
            }

            else -> true
        }
    }

    /**
     * Validates the phone number to ensure it follows a standard format, is not empty,
     * and falls within the length constraints (10–15 digits).
     *
     * @return `true` if the phone number is valid; `false` otherwise.
     */
    private fun isPhoneNumberValid(): Boolean {
        val phone = _state.value.phoneNumber

        return when {
            phone.isEmpty() -> {
                updateState { copy(errorMessage = "Phone number is required.") }
                false
            }

            !Regex("^\\+?[1-9]\\d{1,14}\$").matches(phone) -> {
                updateState { copy(errorMessage = "Invalid phone number format. Please provide a valid number (e.g., +123456789).") }
                false
            }

            phone.length < 10 -> {
                updateState { copy(errorMessage = "Phone number must be at least 10 digits long.") }
                false
            }

            phone.length > 15 -> {
                updateState { copy(errorMessage = "Phone number cannot exceed 15 digits.") }
                false
            }

            else -> {
                true
            }
        }
    }

    /**
     * Ensures that all necessary terms and conditions are accepted.
     *
     * @return `true` if all required terms are checked; `false` otherwise.
     */
    fun isTermsAndConditionsChecked(): Boolean =
        if (_state.value.termsAndConditions && _state.value.privacyPolicy && _state.value.dataAnalytics)
            true
        else {
            updateState { copy(errorMessage = "Please accept all terms and conditions.") }
            false
        }

    /**
     * Formats the phone number by removing invalid characters and applying a standard format
     * (e.g., "+52 XXX-XXX-XXXX").
     *
     * @param phoneNumber The raw phone number provided by the user.
     * @return The formatted phone number as a [String].
     */
    private fun formatPhoneNumber(phoneNumber: String): String = phoneNumber
        .replace(Regex("[^0-9]"), "")
        .take(10)
        .let { digits ->
            if (digits.length == 10) {
                "+52 ${digits.substring(0, 3)}-${digits.substring(3, 6)}-${digits.substring(6)}"
            } else {
                digits
            }
        }

    /**
     * Capitalizes the first letter of each word in the user's name.
     *
     * @return The capitalized name as a [String].
     */
    private fun capitalizeWords(): String =
        _state.value.name.split(" ")
            .joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } }

    /**
     * Attempts to register the user by interacting with the [RegisterRepository].
     * Handles state updates for loading, errors, and success based on the result.
     *
     * @param onSuccess A callback executed when the registration is successful.
     * @param onError A callback executed when the registration fails.
     */
    fun register(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val result = registerRepository.register(
                    email = _state.value.email,
                    password = encryptPasswordSHA256(_state.value.password),
                    name = _state.value.name,
                    curp = _state.value.curp,
                    phoneNumber = formatPhoneNumber(_state.value.phoneNumber)
                )
                if (result.uid != "") {
                    onSuccess()
                } else {
                    onError()
                    _state.value = _state.value.copy(isError = true)
                    _state.value = _state.value.copy(
                        errorMessage = result.message
                    )
                    println(result.message)
                }
            } catch (e: Exception) {
                onError()
                _state.value = _state.value.copy(isError = true)
                _state.value = _state.value.copy(
                    errorMessage = e.message ?: "Error connecting to server"
                )
                println(e.message)
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}