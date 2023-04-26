package com.abdulaziz.currencyconversion.utils

import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue
import platform.Foundation.valueForKey

actual class Prefs {
    companion object {
        const val TIME = "time"
    }

    actual fun saveLong(time: Long) {
        NSUserDefaults.standardUserDefaults.setValue(time, TIME)
    }

    actual fun getLong(): Long {
        return NSUserDefaults.standardUserDefaults.valueForKey(TIME) as Long
    }
}