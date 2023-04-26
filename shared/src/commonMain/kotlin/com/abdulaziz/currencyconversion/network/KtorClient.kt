package com.abdulaziz.currencyconversion.network

import com.abdulaziz.currencyconversion.defaultPlatformEngine
import com.abdulaziz.currencyconversion.httpLogs
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {


    val httpClient = HttpClient(defaultPlatformEngine) {

        install(HttpTimeout) {
            socketTimeoutMillis = 30000
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
        }

        install(Logging) {

            level = LogLevel.BODY
            logger = object : Logger {
                override fun log(message: String) {
                    httpLogs("HTTP> $message")
                }
            }
        }
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true

            })
        }




        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}