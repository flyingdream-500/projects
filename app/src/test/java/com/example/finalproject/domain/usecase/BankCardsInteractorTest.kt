package com.example.finalproject.domain.usecase

import com.example.finalproject.args.DEFAULT_BANK_CARD
import com.example.finalproject.domain.repository.BankCardRepository
import com.example.finalproject.utils.common.Abbreviation.USD
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Тестирование интерактора [BankCardsInteractorImpl] по работе с получением банковских карт
 */
class BankCardsInteractorTest {

    private val bankCardsRepository: BankCardRepository = mockk()

    private val bankCardInteractor = BankCardsInteractorImpl(bankCardsRepository)

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        every { bankCardsRepository.getBankCards() } returns Single.fromCallable {
            arrayListOf(
                DEFAULT_BANK_CARD
            )
        }
        every { bankCardsRepository.getBankCardByAbbr(USD.name) } returns Single.fromCallable { DEFAULT_BANK_CARD }
        every { bankCardsRepository.addAndGetCards(DEFAULT_BANK_CARD) } returns Single.fromCallable {
            arrayListOf(
                DEFAULT_BANK_CARD
            )
        }
    }

    /**
     * Тестирование [BankCardsInteractorImpl.getBankCards]
     */
    @Test
    fun getBankCardsTest() {
        val expectedValue = listOf(DEFAULT_BANK_CARD)
        val result = bankCardInteractor.getBankCards().blockingGet()
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование [BankCardsInteractorImpl.getBankCardByAbbr]
     */
    @Test
    fun getBankCardByAbbrTest() {
        val expectedValue = DEFAULT_BANK_CARD
        val result = bankCardInteractor.getBankCardByAbbr(USD.name).blockingGet()
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование [BankCardsInteractorImpl.addAndGetCards]
     */
    @Test
    fun addAndGetCardsTest() {
        val expectedValue = listOf(DEFAULT_BANK_CARD)
        val result = bankCardInteractor.addAndGetCards(DEFAULT_BANK_CARD).blockingGet()
        assertEquals(expectedValue, result)
    }
}