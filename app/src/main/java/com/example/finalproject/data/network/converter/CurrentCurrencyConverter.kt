package com.example.finalproject.data.network.converter

import com.example.finalproject.model.network.CurrentCurrency
import com.squareup.moshi.JsonAdapter

interface CurrentCurrencyConverter {
    var currencyJsonAdapter: JsonAdapter<CurrentCurrency>?

    fun convertToCurrentCurrency(source: String): CurrentCurrency?

    fun convertToJson(currentCurrency: CurrentCurrency): String?
}