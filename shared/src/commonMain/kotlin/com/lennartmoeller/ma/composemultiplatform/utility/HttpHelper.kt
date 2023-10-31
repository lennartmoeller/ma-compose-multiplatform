package com.lennartmoeller.ma.composemultiplatform.utility

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
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
                throw Exception("Failed to load data from the API")
            }
            return Json.decodeFromString(response.bodyAsText())
        }
    }
}
