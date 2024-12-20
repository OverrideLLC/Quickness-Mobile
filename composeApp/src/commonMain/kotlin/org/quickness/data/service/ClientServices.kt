package org.quickness.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.get
import io.ktor.http.headers
import org.quickness.utils.`object`.Constants.URL_BACK_END

class ClientServices(private val httpClient: HttpClient) {
    suspend fun downloadUserData(): ByteArray {
        return try {
            httpClient.get(URL_BACK_END) {
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