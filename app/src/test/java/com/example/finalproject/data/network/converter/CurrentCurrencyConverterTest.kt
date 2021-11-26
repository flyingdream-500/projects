package com.example.finalproject.data.network.converter

import com.example.finalproject.args.CURRENT_CURRENCY
import com.example.finalproject.args.JSON_CURRENT_CURRENCY
import com.example.finalproject.model.network.CurrentCurrency
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Тест класса конвертации модели в JSON и в [CurrentCurrency]
 */
class CurrentCurrencyConverterTest {

    private lateinit var currencyConverterImpl: CurrentCurrencyConverterImpl

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     * Инициализируем moshi адаптер
     */
    @Before
    fun setUp() {
        val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        currencyConverterImpl = CurrentCurrencyConverterImpl(moshi)
    }


    /**
     * Тестирование [CurrentCurrencyConverterImpl.convertToJson]
     */
    @Test
    fun convertToJsonTest() {
        val expectedValue = JSON_CURRENT_CURRENCY
        val result = currencyConverterImpl.convertToJson(CURRENT_CURRENCY)
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование [CurrentCurrencyConverterImpl.convertToCurrentCurrency]
     */
    @Test
    fun convertToCurrentCurrencyTest() {
        val expectedValue = CURRENT_CURRENCY
        val result = currencyConverterImpl.convertToCurrentCurrency(JSON_CURRENT_CURRENCY)
        assertEquals(expectedValue, result)
    }
}