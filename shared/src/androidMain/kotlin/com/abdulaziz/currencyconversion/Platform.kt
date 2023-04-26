package com.abdulaziz.currencyconversion

import android.util.Log
import io.ktor.client.engine.*
import io.ktor.client.engine.android.*

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()


actual fun httpLogs(string: String) {
    Log.d("Http-",string)
}
actual val defaultPlatformEngine: HttpClientEngine = Android.create()

