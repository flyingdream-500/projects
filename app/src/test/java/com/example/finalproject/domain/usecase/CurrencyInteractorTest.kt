package com.example.finalproject.domain.usecase

import com.example.finalproject.args.CURRENT_CURRENCY
import com.example.finalproject.args.CURRENT_CURRENCY_ITEM
import com.example.finalproject.data.repository.CurrencyRepositoryImpl
import com.example.finalproject.domain.converter.CurrentCurrencyItemConverter
import com.example.finalproject.domain.repository.CurrencyRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


/**
 * Тестирование интерактора [CurrencyInteractorImpl] по работе с получением курсов валют
 */
class CurrencyInteractorTest {

    private val currencyRepository: CurrencyRepository = mockk()
    private val converter: CurrentCurrencyItemConverter = mockk()

    private val currencyInteractor = CurrencyInteractorImpl(currencyRepository, converter)

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        every { currencyRepository.getCurrentCurrencyItem() } returns Maybe.fromCallable {
            CURRENT_CURRENCY_ITEM
        }
        every { currencyRepository.addCurrentCurrencyItem(CURRENT_CURRENCY_ITEM) } returns Completable.fromCallable {  }
        every { currencyRepository.getRemoteCurrentCurrency() } returns Single.fromCallable {
            CURRENT_CURRENCY
        }
        every { converter.convertToCurrentCurrencyItem(CURRENT_CURRENCY) } returns CURRENT_CURRENCY_ITEM
    }

    /**
     * Тестирование [CurrencyInteractorImpl.getCurrentCurrencyItem]
     */
    @Test
    fun getCurrentCurrencyItemTest() {
        val expectedValue = CURRENT_CURRENCY_ITEM
        val result = currencyInteractor.getCurrentCurrencyItem().blockingGet()
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование [CurrencyInteractorImpl.getRemoteCurrentCurrency]
     */
    @Test
    fun getRemoteCurrentCurrencyTest() {
        val expectedValue = CURRENT_CURRENCY_ITEM
        val result = currencyInteractor.getRemoteCurrentCurrency().blockingGet()
        assertEquals(expectedValue, result)
    }
}