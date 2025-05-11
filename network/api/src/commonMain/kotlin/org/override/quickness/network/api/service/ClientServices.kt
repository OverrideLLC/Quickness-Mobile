package org.override.quickness.network.api.service

interface ClientServices {
    suspend fun downloadUserData(): ByteArray
}