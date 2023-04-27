package com.abdulaziz.currencyconversion.android.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulaziz.currencyconversion.android.ui.HomeViewState
import com.abdulaziz.currencyconversion.data.local.DatabaseDriverFactory
import com.abdulaziz.currencyconversion.data.model.CurrencyRateData
import com.abdulaziz.currencyconversion.network.ApiState
import com.abdulaziz.currencyconversion.usecase.DefaultCurrencyUseCase
import com.abdulaziz.currencyconversion.usecase.LocalUseCase
import com.abdulaziz.currencyconversion.utils.CurrencyUtils
import com.abdulaziz.currencyconversion.utils.Prefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    companion object {
        val TIME_FOR_REFRESH = TimeUnit.MINUTES.toMillis(30)
        const val DEFAULT_CURRENCY = "USD"
        const val DEFAULT_BASE_VALUE = 1.0
    }

    private val useCase = DefaultCurrencyUseCase()
    val currencyList = CurrencyUtils.selectableCurrency
    lateinit var localUseCase: LocalUseCase
    private val decimalPattern = Regex("^\\d*\\.?\\d*\$")
    private lateinit var preference: Prefs

    var baseValue by mutableStateOf("")

    private val _homeState = MutableStateFlow<HomeViewState>(HomeViewState())
    val homeState: StateFlow<HomeViewState> = _homeState.asStateFlow()

    fun init(context: Context) {
        preference = Prefs(context)
        localUseCase = LocalUseCase(DatabaseDriverFactory(context))
        refreshData()
    }

    private fun refreshData() {
        val dataAvailable = localUseCase.dataAvailable()
        if (verifyTimeConstraintsAvailable() && dataAvailable) {
            getLocalData()
        } else {
            getRemoteData()
        }
    }

    private fun verifyTimeConstraintsAvailable() =
        preference.getLong() != 0L && (System.currentTimeMillis() - preference.getLong()) < TIME_FOR_REFRESH

    private fun getRemoteData() {
        viewModelScope.launch {
            useCase.getAllRates().collectLatest {
                _homeState.value = when (it) {
                    is ApiState.Success -> saveData(it.result.orEmpty())
                    is ApiState.Failure -> HomeViewState(currencyData = emptyList(), error = (it.error), isLoading = false)
                    is ApiState.Loading -> HomeViewState(error = null, isLoading = true)
                }
            }
        }
    }

    private fun saveData(data: List<CurrencyRateData>): HomeViewState {
        if (data.isEmpty()) {
            return HomeViewState(error = "No data available", currencyData = emptyList(), isLoading = false)
        }

        viewModelScope.launch {
            localUseCase.insertAllRates(data)
            preference.saveLong(System.currentTimeMillis())
        }
        return HomeViewState(currencyData = data, isLoading = false, error = null)
    }

    private fun getLocalData() {
        viewModelScope.launch {
            localUseCase.getAllRates().collectLatest {
                _homeState.value = when (it) {
                    is ApiState.Success -> HomeViewState(currencyData = it.result.orEmpty(), isLoading = false, error = null)
                    is ApiState.Failure -> HomeViewState(currencyData = emptyList(), error = (it.error), isLoading = false)
                    is ApiState.Loading -> HomeViewState(error = null, isLoading = true)
                }
            }
        }
    }

    fun convertRate(rate: Double?, selectedCurrency: CurrencyRateData): MutableState<String> {
        if (rate == null) throw IllegalArgumentException("Rate cannot be null")
        if (baseValue.isDigitsOnly().not() && baseValue.matches(decimalPattern).not()) throw IllegalArgumentException("Base should be a number")

        val amountMultiplier = if (baseValue.isEmpty()) DEFAULT_BASE_VALUE else baseValue.toDouble()

        return mutableStateOf(CurrencyUtils.convertRate(rate, amountMultiplier, selectedCurrency).toString())
    }

    fun updatedValue(it: String): String {
        return if (it.isEmpty() || it.matches(decimalPattern)) {
            it
        } else baseValue
    }

    fun updateBaseCurrency(updatedCurrency: String) {
        _homeState.value = _homeState.value.copy(baseCurrency = updatedCurrency)
    }
}