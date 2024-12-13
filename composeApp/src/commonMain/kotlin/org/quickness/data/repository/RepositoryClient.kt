package org.quickness.data.repository

import org.quickness.data.remote.ClientServices

class RepositoryClient(private val clientServices: ClientServices) {
    suspend fun downloadUserData(): ByteArray {
        return clientServices.downloadUserData()
    }
}