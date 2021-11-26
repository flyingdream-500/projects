package com.example.finalproject.domain.converter

import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.model.network.CurrentCurrency
import com.example.finalproject.model.network.Rates


/**
 * Интерфейс для конвертации данных о курсах валют [CurrentCurrency] в класс для отображения [CurrentCurrencyItem]
 */
interface CurrentCurrencyItemConverter {

    /**
     * Метод для конвертации [CurrentCurrency] в [CurrentCurrencyItem]
     * @param currentCurrency - класс курсов валют
     */
    fun convertToCurrentCurrencyItem(currentCurrency: CurrentCurrency) : CurrentCurrencyItem

    /**
     * Метод для конвертации списка [Rates] в список [Currency]
     * @param rates - список курсов валют
     */
    fun convertToCurrency(rates: Rates): List<Currency>

}