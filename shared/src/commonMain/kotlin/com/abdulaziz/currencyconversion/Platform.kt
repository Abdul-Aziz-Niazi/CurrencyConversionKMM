package com.abdulaziz.currencyconversion

import io.ktor.client.engine.*

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


expect fun httpLogs(string: String)

expect val defaultPlatformEngine: HttpClientEngine