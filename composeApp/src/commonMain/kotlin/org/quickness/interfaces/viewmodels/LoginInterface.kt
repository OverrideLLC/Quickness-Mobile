package org.quickness.interfaces.viewmodels

import kotlinx.coroutines.flow.StateFlow
import org.quickness.network.response.AuthResponse
import org.quickness.ui.states.LoginState

/**
 * Represents the interface for managing the login process.
 *
 * This interface defines the core functionalities required for handling user authentication,
 * including managing the login state, updating the state, initiating the login process,
 * handling success and error scenarios, and validating user input.
 *
 * Implementations of this interface will typically be responsible for orchestrating the
 * entire login workflow, from the initial user input to the final success or failure state.
 *
 * This interface uses a [StateFlow] to represent the current state of the login process,
 * allowing for reactive updates to UI elements or other components based on changes to the login state.
 */
interface LoginInterface {
    /**
     * Represents the current state of the login process.
     *
     * This [StateFlow] emits [LoginState] instances, reflecting the different stages
     * and outcomes of the login attempt. Subscribers to this flow will receive updates
     * whenever the login state changes, such as when the user is initially prompted
     * for credentials, when the login process is in progress, when the login succeeds,
     * or when an error occurs.
     *
     * Observing this [StateFlow] allows UI elements to update dynamically based on the
     * current state of the login process.
     *
     * @see LoginState
     * @see StateFlow
     */
    val state: StateFlow<LoginState>

    /**
     * Updates the current [LoginState] by applying the given [update] function.
     *
     * This function allows you to modify the internal state of the [LoginState] in a safe and
     * predictable way.  It takes a lambda function ([update]) that receives the current
     * [LoginState] as a receiver and returns a new [LoginState] representing the updated state.
     *
     * Example:
     * ```kotlin
     * // Assume we have an instance of a class that holds LoginState called loginManager
     * loginManager.update {
     *     copy(isLoading = true) // Set the isLoading flag to true
     * }
     *
     * loginManager.update {
     *   copy(isLoggedIn = true, username = "testUser") // Set logged in to true and username
     * }
     *
     * loginManager.update {
     *   copy(error = "Invalid Credentials") // set an error message
     * }
     *
     * ```
     *
     * @param update A lambda function that takes the current [LoginState] as a receiver
     *               and returns a new [LoginState] representing the updated state.
     *               The `LoginState.()` syntax means this lambda is an extension function on
     *               [LoginState], allowing you to access the properties of the state directly
     *               within the lambda without needing to explicitly reference it.
     * @throws IllegalStateException if update function is not correctly changing the state. This will depend on the implementation.
     *
     * @see LoginState
     */
    fun update(update: LoginState.() -> LoginState)

    /**
     * Attempts to log in the user.
     *
     * This function simulates a login process. It does not perform actual network requests
     * but rather demonstrates a pattern for handling success and error scenarios.
     *
     * @param onSuccess A callback function to be executed when the login is successful.
     *                  This callback receives no parameters.
     * @param onError A callback function to be executed when the login fails.
     *                This callback receives a single parameter, a String, representing the error message.
     *
     * @sample
     * ```kotlin
     * login(
     *     onSuccess = {
     *         println("Login successful!")
     *         // Navigate to the home screen or perform other actions
     *     },
     *     onError = { errorMessage ->
     *         println("Login failed: $errorMessage")
     *         // Display an error message to the user
     *     }
     * )
     * ```
     */
    fun login(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    /**
     * Handles the successful login scenario.
     *
     * This function is responsible for processing the successful login response,
     * potentially performing actions like saving user data locally, navigating
     * to the next screen, or updating the UI to reflect the logged-in state.
     *
     * @param loginResult The [AuthResponse] object containing the result of the login attempt.
     *                      This object likely holds information such as user details, tokens, etc.
     * @param onSuccess A lambda function that will be invoked when the login process is deemed
     *                  successful. This is typically used to trigger actions like navigation
     *                  or UI updates.
     * @param onError A lambda function that will be invoked if an error occurs during the
     *                success handling process. This function accepts a String parameter
     *                representing the error message. This is useful for scenarios where
     *                additional operations after a successful login (e.g., saving data) might fail.
     *
     * @throws Exception Any exceptions that might be thrown during the internal operation of this function.
     *                    Specific exceptions will depend on the implementation details.
     *
     * @see AuthResponse
     */
    suspend fun loginSuccess(
        loginResult: AuthResponse,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    /**
     * Validates the inputs provided to a function or process.
     *
     * This function serves as a general-purpose validator that can be used to check if
     * the necessary inputs for an operation are valid before proceeding. If any validation
     * fails, it triggers an error callback with a descriptive message.
     *
     * @param onError A lambda function that accepts a String (error message) as input.
     *                This lambda is invoked if any of the input validations fail.
     *                It's responsible for handling the error, such as displaying an
     *                error message to the user or logging the issue.
     * @return `true` if all inputs are valid; `false` if any input fails validation.
     *
     * Example Usage:
     * ```kotlin
     * fun myOperation(name: String, age: Int) {
     *     val isValid = validateInputs { errorMessage ->
     *         println("Validation Error: $errorMessage")
     *         // Handle the error (e.g., show a dialog, log the error)
     *     }
     *
     *     if (isValid) {
     *         // Proceed with the operation since inputs are valid.
     *         println("Inputs are valid. Proceeding...")
     *         // ... your operation logic ...
     *     } else {
     *        println("Input is not valid")
     *     }
     * }
     *
     * myOperation("John Doe", 30) // Expected to pass validation
     * myOperation("", 0) // Expected to fail validation
     * ```
     */
    fun validateInputs(onError: (String) -> Unit): Boolean

    /**
     * Handles login errors by invoking a callback with the provided error message.
     *
     * This function is designed to be called when a login attempt fails. It takes an error message
     * describing the failure and a callback function. The callback function is then invoked with
     * the error message, allowing the caller to handle the error appropriately (e.g., displaying
     * the error message to the user, logging the error, etc.).
     *
     * @param errorMessage The error message describing the login failure. This should be a human-readable
     *                     string that can be displayed to the user or used for debugging purposes.
     * @param onError A callback function that accepts a single string argument (the error message).
     *                This function will be invoked with the `errorMessage` when a login error occurs.
     */
    fun loginError(
        errorMessage: String,
        onError: (String) -> Unit
    )
}