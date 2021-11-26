package com.example.finalproject.data.network.converter

import com.example.finalproject.model.network.CurrentCurrency
import com.squareup.moshi.JsonAdapter

/**
 * Интерфейс для преобразования [CurrentCurrency] в JSON и обратно с помощью Moshi
 */
interface CurrentCurrencyConverter {
    /**
     * Адаптер moshi
     */
    var currencyJsonAdapter: JsonAdapter<CurrentCurrency>?

    /**
     * Метод конвертации в класс
     * @param source - JSON
     */
    fun convertToCurrentCurrency(source: String): CurrentCurrency?

    /**
     * Метод конвертации в JSON
     * @param currentCurrency - класс c данными о курсах валют
     */
    fun convertToJson(currentCurrency: CurrentCurrency): String?
}