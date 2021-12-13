package com.example.finalproject.domain.converter

import com.example.finalproject.args.CURRENT_CURRENCY
import com.example.finalproject.args.CURRENT_CURRENCY_ITEM
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.model.network.CurrentCurrency
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Тест класса конвертации модели [CurrentCurrency] в [CurrentCurrencyItem]
 */
class CurrentCurrencyItemTest {

    private lateinit var converter: CurrentCurrencyItemConverter


    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        converter = CurrentCurrencyItemConverterImpl()
    }


    /**
     * Тестирование [CurrentCurrencyItemConverterImpl.convertToCurrentCurrencyItem]
     */
    @Test
    fun convertToCurrentCurrencyItemTest() {
        val expectedValue = CURRENT_CURRENCY_ITEM
        val result = converter.convertToCurrentCurrencyItem(CURRENT_CURRENCY)
        assertEquals(expectedValue, result)
    }


    /**
     * Тестирование [CurrentCurrencyItemConverterImpl.convertToCurrency]
     */
    @Test
    fun convertToCurrencyTest() {
        val expectedValue = CURRENT_CURRENCY_ITEM.currencyList
        val result = converter.convertToCurrency(CURRENT_CURRENCY.usd)
        assertEquals(expectedValue, result)
    }
}