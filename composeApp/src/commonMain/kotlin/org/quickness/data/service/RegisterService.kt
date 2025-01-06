package org.quickness.data.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import org.quickness.data.response.ApiResponse
import org.quickness.data.request.RegisterRequest
import org.quickness.utils.`object`.ApiLinks

class RegisterService(private val httpClient: HttpClient) {

    /**
     * Realiza el registro de un usuario en el backend.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param name Nombre del usuario.
     * @param curp CURP del usuario.
     * @param phoneNumber Número de teléfono del usuario.
     * @return [ApiResponse] que contiene el estado del registro y el UID.
     */
    suspend fun register(
        email: String,
        password: String,
        name: String,
        curp: String,
        phoneNumber: String,
    ): ApiResponse {
        return try {
            // Construcción de la solicitud
            val request = RegisterRequest(
                email = email,
                password = password,
                name = name,
                curp = curp,
                phone = phoneNumber
            )

            // Realizar la solicitud HTTP
            httpClient.post {
                url(ApiLinks.REGISTER_API_LINK)
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<ApiResponse>()
        } catch (e: Exception) {
            // Manejo de errores y registro del problema
            Logger.DEFAULT.log("Error en el registro de usuario: ${e.message}")
            ApiResponse(
                status = 500,
                message = "Error en el registro de usuario: ${e.message}",
                data = buildJsonObject { }
            )
        }
    }
}