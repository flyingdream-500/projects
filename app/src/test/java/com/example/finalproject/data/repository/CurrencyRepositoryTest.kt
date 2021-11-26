package com.example.finalproject.data.repository

import com.example.finalproject.args.CURRENT_CURRENCY
import com.example.finalproject.args.CURRENT_CURRENCY_ITEM
import com.example.finalproject.data.db.dao.CurrentCurrencyDao
import com.example.finalproject.data.network.api.CurrencyApi
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


/**
 * Тестирование репозитория [CurrencyRepositoryImpl] по работе с получением курсов валют
 */
class CurrencyRepositoryTest {

    private val currentCurrencyDao: CurrentCurrencyDao = mockk()
    private val currencyApi: CurrencyApi = mockk()

    private val currencyRepository = CurrencyRepositoryImpl(currencyApi, currentCurrencyDao)

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        every { currentCurrencyDao.getCurrentCurrency() } returns Maybe.fromCallable {
            CURRENT_CURRENCY_ITEM
        }
        every { currentCurrencyDao.addCurrentCurrency(CURRENT_CURRENCY_ITEM) } returns Completable.fromCallable {  }
        every { currencyApi.getCurrency() } returns Single.fromCallable {
            CURRENT_CURRENCY
        }
    }

    /**
     * Тестирование [CurrencyRepositoryImpl.getCurrentCurrencyItem]
     */
    @Test
    fun getCurrentCurrencyItemTest() {
        val expectedValue = CURRENT_CURRENCY_ITEM
        val result = currencyRepository.getCurrentCurrencyItem().blockingGet()
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование [CurrencyRepositoryImpl.getRemoteCurrentCurrency]
     */
    @Test
    fun getRemoteCurrentCurrencyTest() {
        val expectedValue = CURRENT_CURRENCY
        val result = currencyRepository.getRemoteCurrentCurrency().blockingGet()
        assertEquals(expectedValue, result)
    }
}