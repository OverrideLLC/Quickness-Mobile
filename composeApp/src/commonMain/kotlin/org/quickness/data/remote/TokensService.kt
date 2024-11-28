package org.quickness.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.quickness.data.model.TokensResult
import org.quickness.data.request.TokensRequest
import org.quickness.utils.`object`.Constants

class TokensService(private val httpClient: HttpClient) {
    suspend fun getTokens(uid: String): TokensResult {
        return try {
            httpClient.post {
                url("${Constants.URL_BACK_END}/tokens")
                contentType(ContentType.Application.Json)
                setBody(
                    TokensRequest(
                        uid = uid
                    )
                )
            }.body()
        } catch (e: Exception) {
            TokensResult(status = e.message.toString(), tokens = mapOf())
        }
    }
}