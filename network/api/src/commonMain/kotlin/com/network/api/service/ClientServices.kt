package com.network.api.service

interface ClientServices {
    suspend fun downloadUserData(): ByteArray
}