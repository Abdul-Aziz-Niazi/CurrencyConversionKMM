package com.abdulaziz.currencyconversion

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


actual fun httpLogs(string: String) {
    print("okhttp:$string")
}

actual val defaultPlatformEngine: HttpClientEngine = Darwin.create()

