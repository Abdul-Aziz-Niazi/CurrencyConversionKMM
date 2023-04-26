package com.abdulaziz.currencyconversion.android.ui

import com.abdulaziz.currencyconversion.android.ui.viewmodels.MainViewModel
import com.abdulaziz.currencyconversion.data.model.CurrencyRateData

data class HomeViewState(
    val isLoading: Boolean = false,
    val baseCurrency: String = MainViewModel.DEFAULT_CURRENCY,
    val currencyData: List<CurrencyRateData> = emptyList(),
    val error: String? = null
)

sealed interface HomeState {
    object LoadingState : HomeState
    class OnData(val data: List<CurrencyRateData>) : HomeState
    class OnError(val error: String?) : HomeState
    class OnBaseCurrencyChange(val currency: String = "USD") : HomeState
}