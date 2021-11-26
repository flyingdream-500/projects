package com.example.finalproject.data.repository

import com.example.finalproject.args.DEFAULT_BANK_CARD
import com.example.finalproject.data.db.dao.BankCardsDao
import com.example.finalproject.utils.common.Abbreviation.USD
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


/**
 * Тестирование репозитория [BankCardRepositoryImpl] по работе с получением банковских карт
 */
class BankCardRepositoryTest {

    private val bankCardsDao: BankCardsDao = mockk()

    private val bankCardsRepository = BankCardRepositoryImpl(bankCardsDao)


    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        every { bankCardsDao.getBankCards() } returns Single.fromCallable {
            arrayListOf(
                DEFAULT_BANK_CARD
            )
        }
        every { bankCardsDao.getBankCardByAbbr(USD.name) } returns Single.fromCallable { DEFAULT_BANK_CARD }
        every { bankCardsDao.addAndGetCards(DEFAULT_BANK_CARD) } returns Single.fromCallable {
            arrayListOf(
                DEFAULT_BANK_CARD
            )
        }
    }

    /**
     * Тестирование [BankCardRepositoryImpl.getBankCards]
     */
    @Test
    fun getBankCardsTest() {
        val expectedValue = listOf(DEFAULT_BANK_CARD)
        val result = bankCardsRepository.getBankCards().blockingGet()
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование [BankCardRepositoryImpl.getBankCardByAbbr]
     */
    @Test
    fun getBankCardByAbbrTest() {
        val expectedValue = DEFAULT_BANK_CARD
        val result = bankCardsRepository.getBankCardByAbbr(USD.name).blockingGet()
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование [BankCardRepositoryImpl.addAndGetCards]
     */
    @Test
    fun addAndGetCardsTest() {
        val expectedValue = listOf(DEFAULT_BANK_CARD)
        val result = bankCardsRepository.addAndGetCards(DEFAULT_BANK_CARD).blockingGet()
        assertEquals(expectedValue, result)
    }
}