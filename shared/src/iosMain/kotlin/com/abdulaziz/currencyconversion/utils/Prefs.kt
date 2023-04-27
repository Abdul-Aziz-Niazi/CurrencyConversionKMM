package com.abdulaziz.currencyconversion.utils

import platform.Foundation.NSUserDefaults
actual class Prefs {
    companion object {
        const val TIME = "time"
    }

    actual fun saveLong(time: Long) {
        NSUserDefaults.standardUserDefaults.setObject(time, TIME)
    }

    actual fun getLong(): Long {
        return NSUserDefaults.standardUserDefaults.objectForKey(TIME) as Long
    }
}