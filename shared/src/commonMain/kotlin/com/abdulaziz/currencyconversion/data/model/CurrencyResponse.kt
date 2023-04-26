package com.abdulaziz.currencyconversion.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse(
    @SerialName("base")
    val base: String?,
    @SerialName("disclaimer")
    val disclaimer: String?,
    @SerialName("license")
    val license: String?,
    @SerialName("rates")
    val rates: Rates?,
    @SerialName("timestamp")
    val timestamp: Long?
)