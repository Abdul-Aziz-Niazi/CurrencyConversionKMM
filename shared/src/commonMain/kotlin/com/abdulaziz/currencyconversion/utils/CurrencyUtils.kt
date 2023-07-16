package com.abdulaziz.currencyconversion.utils

import com.abdulaziz.currencyconversion.data.model.CurrencyRateData

object CurrencyUtils {
    const val TIME_FOR_REFRESH = 30 * 60 * 1000
    const val DEFAULT_CURRENCY = "USD"
    val decimalPattern = Regex("^\\d*\\.?\\d*\$")
    val selectableCurrency = listOf("USD", "EUR", "GBP", "AUD", "CAD", "JPY", "KWD", "AED", "INR", "BTC", "PKR")

    fun convertRate(rate: Double, amount: String, selectedCurrency: CurrencyRateData): String {

        val amountMultiplier = amount.toDouble()
        val convertedResult = if (selectedCurrency.currencyName == DEFAULT_CURRENCY) {
            (rate * amountMultiplier)
        } else {
            (1 / selectedCurrency.rate!!) / (1 / rate) * amountMultiplier
        }
        return convertedResult.toString()
    }
}