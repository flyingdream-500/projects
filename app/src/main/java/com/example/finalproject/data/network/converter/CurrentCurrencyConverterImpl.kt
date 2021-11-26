package com.example.finalproject.data.network.converter

import com.example.finalproject.model.network.CurrentCurrency
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi


/**
 * Реализация интерфейса [CurrentCurrencyConverter] по преобразованию [CurrentCurrency] в JSON и обратно с помощью Moshi
 * @param moshi - экземпляр класса Moshi для конвертации объекта в JSON  и обратно
 */
class CurrentCurrencyConverterImpl(moshi: Moshi) : CurrentCurrencyConverter {

    /**
     * Реализация адаптера moshi
     */
    override var currencyJsonAdapter: JsonAdapter<CurrentCurrency>? =
        moshi.adapter(CurrentCurrency::class.java)

    /**
     * Метод конвертации в класс
     * @param source - JSON
     */
    override fun convertToCurrentCurrency(source: String): CurrentCurrency? {
        return currencyJsonAdapter?.fromJson(source)
    }

    /**
     * Метод конвертации в JSON
     * @param currentCurrency - класс c данными о курсах валют
     */
    override fun convertToJson(currentCurrency: CurrentCurrency): String? {
        return currencyJsonAdapter?.toJson(currentCurrency)
    }

}