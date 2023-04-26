package com.abdulaziz.currencyconversion.network

sealed interface ApiState<out T: Any> {
    class Success<out T: Any>(val result: T?) : ApiState<T>
    class Failure(val error: String?) : ApiState<Nothing>
    object Loading : ApiState<Nothing>
}

