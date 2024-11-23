package org.quickness.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.quickness.data.model.RegisterResult
import org.quickness.data.request.RegisterRequest

class RegisterService(private val httpClient: HttpClient) {
    suspend fun register(
        email: String,
        password: String,
        name: String,
        curp: String,
        phoneNumber: String,
    ): RegisterResult {
        return try {
            httpClient.post {
                url("https://us-central1-quickness-backend-7f4ac.cloudfunctions.net/Registro")
                contentType(ContentType.Application.Json)
                setBody(
                    RegisterRequest(
                        email = email,
                        password = password,
                        name = name,
                        CURP = curp,
                        phone_number = phoneNumber
                    )
                )
            }.body()
        } catch (e: Exception) {
            RegisterResult(uid = "", message = e.message ?: "Error connecting to server")
        }
    }
}