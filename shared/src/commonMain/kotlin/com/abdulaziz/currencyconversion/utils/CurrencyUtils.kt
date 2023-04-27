package com.abdulaziz.currencyconversion.utils

import com.abdulaziz.currencyconversion.data.model.CurrencyRateData

object CurrencyUtils {
    private const val DEFAULT_CURRENCY = "USD"
    val selectableCurrency = listOf("USD", "EUR", "GBP", "AUD", "CAD", "JPY", "KWD", "AED", "INR", "BTC", "PKR")

    fun convertRate(rate: Double, amount: Double, selectedCurrency: CurrencyRateData): Double {
        val convertedResult = if (selectedCurrency.currencyName == DEFAULT_CURRENCY) {
            (rate * amount)
        } else {
            (1 / selectedCurrency.rate!!) / (1 / rate) * amount
        }
        return convertedResult
    }
}