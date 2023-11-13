package com.lennartmoeller.ma.composemultiplatform.util

import com.lennartmoeller.ma.composemultiplatform.entities.PutResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmStatic

class HttpHelper {
    companion object {
        // TODO: Move to config file in future
        const val baseUrl = "http://localhost/api/masters-thesis"

        @JvmStatic
        suspend inline fun <reified T> get(resource: String): T {
            val client = HttpClient()
            val response: HttpResponse = client.get("$baseUrl/$resource")
            if (response.status.value != 200) {
                throw Exception("GET request failed")
            }
            val json = Json { ignoreUnknownKeys = true }
            return json.decodeFromString(response.bodyAsText())
        }

        @JvmStatic
        suspend inline fun <reified T> put(resource: String, element: T): PutResponse {
            val client = HttpClient()
            val response: HttpResponse = client.put("$baseUrl/$resource") {
                setBody(Json.encodeToString(element))
            }
            if (response.status.value != 200) {
                throw Exception("PUT request failed")
            }
            return Json.decodeFromString(response.bodyAsText())
        }
    }
}
