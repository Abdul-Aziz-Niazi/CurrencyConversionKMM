package com.abdulaziz.currencyconversion.data.local

import android.content.Context
import com.abdulaziz.currencyconversion.database.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context:Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context,"currency_rates.db")
    }
}