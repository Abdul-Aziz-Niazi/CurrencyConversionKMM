package com.abdulaziz.currencyconversion.usecase

import com.abdulaziz.currencyconversion.data.local.DatabaseDriverFactory
import com.abdulaziz.currencyconversion.data.model.CurrencyRateData
import com.abdulaziz.currencyconversion.network.ApiState
import com.abdulaziz.currencyconversion.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalUseCase(databaseDriverFactory: DatabaseDriverFactory) : CurrencyUseCase {
    private val repository = LocalRepository(databaseDriverFactory)

    override fun getAllRates(): Flow<ApiState<List<CurrencyRateData>>> = flow {
        emit(ApiState.Loading)
        kotlin.runCatching {
            repository.getAllRates()
        }.onSuccess { data ->
            emit(ApiState.Success(data))
        }.onFailure { error ->
            emit(ApiState.Failure(error.message))
        }
    }

    suspend fun insertAllRates(listOfCurrencyRates: List<CurrencyRateData>) {
        repository.insertAllRates(listOfCurrencyRates)
    }

    fun dataAvailable() = repository.dataAvailable()
}