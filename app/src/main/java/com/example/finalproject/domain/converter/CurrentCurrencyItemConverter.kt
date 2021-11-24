package com.example.finalproject.domain.converter

import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.model.network.Rates
import com.example.finalproject.model.network.CurrentCurrency

interface CurrentCurrencyItemConverter {

    fun convertToCurrentCurrencyItem(currentCurrency: CurrentCurrency) : CurrentCurrencyItem

    fun convertToCurrency(rates: Rates): List<Currency>

}