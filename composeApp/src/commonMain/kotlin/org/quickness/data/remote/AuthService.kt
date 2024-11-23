package org.quickness.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.quickness.data.model.LoginResult
import org.quickness.data.request.LoginRequest

class AuthService(private val httpClient: HttpClient) {
    suspend fun login(email: String, password: String): LoginResult {
        return try {
            httpClient.post{
                url("http://localhost:8080/login")
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }.body()
        }catch (e: Exception){
            LoginResult(isSuccessful = false, errorMessage = e.message)
        }
    }
}