package org.override.quickness.feature.register.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.override.quickness.feature.register.states.RegisterState
import org.override.quickness.shared.resources.interfaces.Resources
import org.override.quickness.shared.resources.interfaces.ResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.override.quickness.network.api.repository.RegisterRepository
import org.override.quickness.shared.utils.objects.ValidatesData

/**
 * ViewModel responsible for managing the state and logic of the registration process.
 * Handles user input validation, state updates, and interaction with the [RegisterRepositoryImpl].
 *
 * @property registerRepository The repository used for handling registration-related operations, such as submitting user data.
 */
class RegisterViewModel(
    private val registerRepository: RegisterRepository,
    private val resources: Resources
) : ViewModel(), ResourcesProvider {

    /**
     * Holds the current state of the registration process as a [org.quickness.ui.states.RegisterState].
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
        ValidatesData.isEmailValid(
            email = _state.value.email,
            errorMessage = { errorMessage -> updateState { copy(errorMessage = errorMessage) } }
        )
                && ValidatesData.isPasswordValid(
            password = _state.value.password,
            errorMessage = { errorMessage -> updateState { copy(errorMessage = errorMessage) } }
        )
                && ValidatesData.confirmPassword(
            password = _state.value.password,
            confirmPassword = _state.value.confirmPassword,
            errorMessage = { errorMessage -> updateState { copy(errorMessage = errorMessage) } }
        )

    /**
     * Validates personal information fields such as name, CURP, and phone number.
     *
     * @return `true` if all personal information fields are valid; `false` otherwise.
     */
    fun validatePersonalInfo(): Boolean =
        ValidatesData.isNameValid(
            errorMessage = { errorMessage -> updateState { copy(errorMessage = errorMessage) } },
            capitalizeWords = { capitalizeWords() },
            name = "${_state.value.lastName} ${_state.value.name}"
        ) && ValidatesData.isCurpValid(
            curp = _state.value.curp,
            day = _state.value.day,
            month = _state.value.month,
            year = _state.value.year,
            sex = _state.value.sex,
            name = "${_state.value.lastName} ${_state.value.name}",
            selectedState = _state.value.selectedState,
            errorMessage = { errorMessage -> updateState { copy(errorMessage = errorMessage) } }
        ) && ValidatesData.isPhoneNumberValid(
            errorMessage = { errorMessage -> updateState { copy(errorMessage = errorMessage) } },
            phone = _state.value.phoneNumber
        )


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
     * Capitalizes the first letter of each word in the user's name.
     *
     * @return The capitalized name as a [String].
     */
    fun capitalizeWords(): String =
        "${_state.value.lastName} ${_state.value.name}".split(" ")
            .joinToString(" ") { word -> word.replaceFirstChar { it.uppercaseChar() } }

    /**
     * Attempts to register the user by interacting with the [RegisterRepositoryImpl].
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
                    password = _state.value.password,
                    name = "${_state.value.lastName} ${_state.value.name}",
                    curp = _state.value.curp,
                    phoneNumber = ValidatesData.formatPhoneNumber(_state.value.phoneNumber)
                )
                if (result.status == 200) {
                    onSuccess()
                } else {
                    onError()
                    _state.value = _state.value.copy(
                        isError = true,
                        errorMessage = result.message
                    )
                    println(result.status)
                }
            } catch (e: Exception) {
                onError()
                _state.value = _state.value.copy(
                    isError = true,
                    errorMessage = e.message ?: "Error connecting to server"
                )
                println(e.message)
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    override fun getDrawable(name: String): DrawableResource {
        return resources.getDrawable(name)
    }
}