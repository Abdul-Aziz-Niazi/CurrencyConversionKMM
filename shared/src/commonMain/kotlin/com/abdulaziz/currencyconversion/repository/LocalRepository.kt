package com.abdulaziz.currencyconversion.repository

import com.abdulaziz.currencyconversion.data.local.DatabaseDriverFactory
import com.abdulaziz.currencyconversion.data.model.CurrencyRateData
import com.abdulaziz.currencyconversion.database.AppDatabase
import com.abdulaziz.currencyconversion.database.CurrencyRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepository(dbDriverFactory: DatabaseDriverFactory) {

    private val db = AppDatabase(dbDriverFactory.createDriver())
    private val currencyRatesQueries = db.appDatabaseQueries

    suspend fun insertAllRates(rateList: List<CurrencyRateData>) {
        withContext(Dispatchers.Default) {
            rateList.forEach {
                currencyRatesQueries.insertCurrencyRate(
                    it.currencyName,
                    it.rate
                )
            }
        }
    }

    suspend fun getAllRates(): List<CurrencyRateData> {
        return withContext(Dispatchers.Default) {
            currencyRatesQueries.getAllCurrencyRates().executeAsList().map {
                it.toModel()
            }
        }
    }

    fun dataAvailable(): Boolean =
        currencyRatesQueries.getAllCurrencyRates().executeAsList().isNotEmpty()

}

fun CurrencyRates.toModel(): CurrencyRateData {
    return CurrencyRateData(
        currencyName = currencyName,
        rate = currencyRate
    )
}