package com.network.api.repository

/**
 * Repository interface for managing client-related operations.
 *
 * This interface defines methods for interacting with client data,
 * such as downloading user data. Implementations of this interface
 * should provide concrete logic for accessing and managing client information.
 */
interface ClientRepository {
    /**
     * Downloads the user's data.
     *
     * This function downloads the user's data from a remote source
     * and returns it as a byte array.
     *
     * @return The user's data as a byte array.
     * @throws [Exception] If an error occurs during the download process.
     */
    suspend fun downloadUserData(): ByteArray
}