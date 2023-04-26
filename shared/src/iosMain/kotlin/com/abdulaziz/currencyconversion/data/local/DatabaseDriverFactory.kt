package com.abdulaziz.currencyconversion.data.local

import com.abdulaziz.currencyconversion.database.AppDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {

    actual fun createDriver() : SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema,"fact.db")
    }
}