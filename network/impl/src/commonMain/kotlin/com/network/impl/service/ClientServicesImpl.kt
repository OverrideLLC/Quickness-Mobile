package com.network.impl.service

import com.network.api.service.ClientServices
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.get
import io.ktor.http.headers

class ClientServicesImpl(private val httpClient: HttpClient) : ClientServices {
    override suspend fun downloadUserData(): ByteArray {
        return try {
            httpClient.get {
                headers {
                    append("Content-Type", "application/json")
                }
            }.body()
        } catch (e: Exception) {
            Logger.DEFAULT.log(e.message.toString())
            return ByteArray(0)
        }
    }
}