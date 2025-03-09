package com.network.api.service

import com.network.api.response.AuthResponse
import com.network.api.response.ForgotPasswordResponse

/**
 * An interface that defines the contract for Firebase Authentication operations.
 *
 * This interface provides methods for common authentication tasks, such as signing in,
 * signing out, resetting passwords, and managing user accounts.
 *
 * Implementations of this interface should interact with the Firebase Authentication SDK
 * to perform the actual authentication operations.
 */
interface FirebaseAuth {

    /**
     * Signs in a user with the provided email and password.
     *
     * This function attempts to authenticate a user using their email and password.
     * It returns an [com.network.api.response.AuthResponse] object, which indicates whether the sign-in was successful
     * and provides information about the authenticated user.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return An [com.network.api.response.AuthResponse] object representing the result of the sign-in attempt.
     *
     * @throws [FirebaseAuthException] If an error occurs during the sign-in process.
     * This could be due to invalid credentials, network issues, or other authentication errors.
     * Check the exception message for more details.
     */
    suspend fun signIn(email: String, password: String): AuthResponse

    /**
     * Initiates the password reset process for a user with the given email address.
     *
     * This function sends a password reset email to the specified email address if a user
     * with that email is found. The email will contain instructions on how to reset
     * their password.
     *
     * @param email The email address of the user who wants to reset their password.
     * @return A [com.network.api.response.ForgotPasswordResponse] object indicating the outcome of the operation.
     *         This can be either `Success` if the email was sent successfully, or
     *         `Error` with a specific error code if something went wrong.
     *         Returns `null` if the user is not found.
     *
     * @throws Exception If an unexpected error occurs during the process. This might be
     *                   due to network issues, database errors, or other unforeseen
     *                   circumstances.
     */
    suspend fun forgotPassword(email: String): ForgotPasswordResponse?

    /**
     * Reauthenticates the user with their current password and then changes their password to a new one.
     *
     * This function first attempts to reauthenticate the user using their email and current password.
     * If reauthentication is successful, it proceeds to update the user's password to the new password provided.
     *
     * @param email The user's email address.
     * @param currentPassword The user's current password.
     * @param newPassword The desired new password for the user.
     * @param onSuccess A callback function that is invoked if the password change is successful.
     * @param onError A callback function that is invoked if an error occurs during the process,
     *                 passing the exception as an argument.
     */
    suspend fun reauthenticateAndChangePassword(
        email: String,
        currentPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    /**
     * Changes the user's password.
     *
     * This function attempts to change the user's password to the given `newPassword`.
     * Upon successful password change, the `onSuccess` callback is invoked.
     * If an error occurs during the process, the `onError` callback is invoked with the corresponding Exception.
     *
     * @param newPassword The new password to be set.
     * @param onSuccess A callback function that is executed when the password change is successful.
     * @param onError A callback function that is executed when an error occurs during the password change process.
     *                  It receives the Exception that caused the error as a parameter.
     */
    fun changePassword(newPassword: String, onSuccess: () -> Unit, onError: (Exception) -> Unit)

    /**
     * Changes the user's email address.
     *
     * @param newEmail The new email address to be set.
     * @param onSuccess A callback function that is executed when the email change is successful.
     * @param onError A callback function that is executed when an error occurs during the email change process.
     *                It receives the exception as a parameter.
     */
    fun changeEmail(newEmail: String, onSuccess: () -> Unit, onError: (Exception) -> Unit)

    /**
     * Logs the user out.
     *
     * This function attempts to log the user out. If successful, it invokes the `onSuccess` lambda.
     * If an error occurs during the logout process, it invokes the `onError` lambda with the exception.
     *
     * @param onSuccess A lambda function to be executed upon successful logout.
     * @param onError A lambda function to be executed if an error occurs during logout. It receives the exception as a parameter.
     */
    suspend fun logOut(onSuccess: () -> Unit, onError: (Exception) -> Unit)

    /**
     * Reauthenticates the currently signed-in user.
     *
     * This function attempts to reauthenticate the user using their email and current password.
     * It is typically used before performing sensitive operations that require recent authentication,
     * such as changing the user's password or email.
     *
     * @param email The user's email address.
     * @param currentPassword The user's current password.
     * @param onSuccess A callback function that is executed if the reauthentication is successful.
     * @param onError A callback function that is executed if the reauthentication fails.
     *                The function receives an [Exception] object containing information about the error.
     */
    suspend fun reauthenticate(
        email: String,
        currentPassword: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )
}