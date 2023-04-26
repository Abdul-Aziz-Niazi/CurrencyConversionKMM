package com.abdulaziz.currencyconversion.repository

import com.abdulaziz.currencyconversion.data.model.CurrencyResponse
import com.abdulaziz.currencyconversion.network.KtorClient
import com.abdulaziz.currencyconversion.network.NetworkConstants
import io.ktor.client.call.*
import io.ktor.client.request.*

interface CurrencyRepository {
    @Throws(Exception::class)
    suspend fun getAllRates(): CurrencyResponse
}

class DefaultCurrencyRepository : CurrencyRepository {
    private val client = KtorClient.httpClient
    override suspend fun getAllRates(): CurrencyResponse =
        client.get(NetworkConstants.BASE_URL) {
            parameter(NetworkConstants.PARAM_APP_ID, NetworkConstants.APP_ID)
            parameter(NetworkConstants.PARAM_BASE, NetworkConstants.BASE_CURRENCY)
        }.body()
}