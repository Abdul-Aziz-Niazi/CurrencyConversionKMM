package com.abdulaziz.currencyconversion.utils

import android.content.Context
import android.content.SharedPreferences


actual class Prefs(private val context: Context) {
    companion object {
        const val TIME = "time"
        const val PREFS_NAME = "CurrencyConversion"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    actual fun saveLong(time: Long) {
        sharedPreferences.edit().putLong(TIME, time).apply()
    }

    actual fun getLong(): Long {
        return sharedPreferences.getLong(TIME, 0L)
    }
}
