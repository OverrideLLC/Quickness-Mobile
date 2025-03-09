package com.network.impl.repository

import com.network.api.repository.ClientRepository
import com.network.api.service.ClientServices

/**
 * An implementation of the `ClientRepository` interface.
 *
 * This class uses an instance of `ClientServices` to interact with
 * the client data source and perform operations such as downloading
 * user data.
 *
 * @property clientServices The `ClientServices` instance used for data access.
 */
class ClientRepositoryImpl(
    private val clientServices: ClientServices
) : ClientRepository {
    /**
     * Downloads the user's data.
     *
     * This function uses the `clientServices` to retrieve the user's data as a byte array.
     *
     * @return A byte array representing the user's data.
     */
    override suspend fun downloadUserData(): ByteArray {
        return clientServices.downloadUserData()
    }
}