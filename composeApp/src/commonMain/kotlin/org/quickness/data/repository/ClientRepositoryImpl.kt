package org.quickness.data.repository

import org.quickness.data.service.ClientServices
import org.quickness.interfaces.repository.ClientRepository

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