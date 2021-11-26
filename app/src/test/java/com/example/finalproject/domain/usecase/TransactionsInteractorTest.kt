package com.example.finalproject.domain.usecase

import com.example.finalproject.args.CURRENT_TRANSACTION
import com.example.finalproject.domain.repository.TransactionsRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Maybe
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Тестирование интерактора [TransactionsInteractorImpl] по работе с выполненными транзакциями
 */
class TransactionsInteractorTest {

    private val transactionsRepository: TransactionsRepository = mockk()

    private val transactionsInteractor = TransactionsInteractorImpl(transactionsRepository)

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        every { transactionsRepository.getTransactions() } returns Maybe.fromCallable {
            arrayListOf(CURRENT_TRANSACTION)
        }
    }


    /**
     * Тестирование [TransactionsInteractorImpl.getTransactions]
     */
    @Test
    fun getTransactionsTest() {
        val expectedValue = arrayListOf(CURRENT_TRANSACTION)
        val result = transactionsInteractor.getTransactions().blockingGet()
        assertEquals(expectedValue, result)
    }

}