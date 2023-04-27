package com.abdulaziz.currencyconversion.usecase

import com.abdulaziz.currencyconversion.data.model.CurrencyRateData
import com.abdulaziz.currencyconversion.data.model.Rates
import com.abdulaziz.currencyconversion.httpLogs
import com.abdulaziz.currencyconversion.network.ApiState
import com.abdulaziz.currencyconversion.repository.DefaultCurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CurrencyUseCase {

    fun getAllRates():Flow<ApiState<List<CurrencyRateData>>>
}

class DefaultCurrencyUseCase: CurrencyUseCase{
    val selectableCurrency = listOf("USD", "EUR", "GBP", "AUD", "CAD", "JPY", "KWD", "AED", "INR", "BTC", "PKR")

    private val repository = DefaultCurrencyRepository()
    override fun getAllRates(): Flow<ApiState<List<CurrencyRateData>>> = flow {
        emit(ApiState.Loading)
        kotlin.runCatching {
            repository.getAllRates()
        }.onSuccess {
            emit(ApiState.Success(getListOfRates(it.rates)))
        }.onFailure {
            emit(ApiState.Failure(it.message))
        }
    }

    private fun getListOfRates(rates: Rates?): List<CurrencyRateData>? {
        val listOfRates = mutableListOf<CurrencyRateData>()
        rates?.toMap()?.map {
            listOfRates.add(CurrencyRateData(it.key, it.value))
        }
        return listOfRates
    }
}

