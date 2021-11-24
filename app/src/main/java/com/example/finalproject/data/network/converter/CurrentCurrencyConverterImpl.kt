package com.example.finalproject.data.network.converter

import com.example.finalproject.model.network.CurrentCurrency
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi


/**
 * Конвертер для преобразования [CurrentCurrency] в JSON и обратно с помощью Moshi,
 * который создается в [com.example.cleararchcurrency.presentation.viewmodel.ViewModelFactory]
 */
class CurrentCurrencyConverterImpl(moshi: Moshi) : CurrentCurrencyConverter {

    override var currencyJsonAdapter: JsonAdapter<CurrentCurrency>? =
        moshi.adapter(CurrentCurrency::class.java)

    override fun convertToCurrentCurrency(source: String): CurrentCurrency? {
        return currencyJsonAdapter?.fromJson(source)
    }

    override fun convertToJson(currentCurrency: CurrentCurrency): String? {
        return currencyJsonAdapter?.toJson(currentCurrency)
    }

}