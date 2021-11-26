package com.example.finalproject.data.repository

import com.example.finalproject.args.CURRENT_TRANSACTION
import com.example.finalproject.data.db.dao.TransactionsDao
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Maybe
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


/**
 * Тестирование репозитория [TransactionsRepositoryImpl]  по работе с выполненными транзакциями
 */
class TransactionsRepositoryTest {

    private val transactionsDao: TransactionsDao = mockk()

    private val transactionsRepository = TransactionsRepositoryImpl(transactionsDao)

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        every { transactionsDao.getTransactions() } returns Maybe.fromCallable {
            arrayListOf(CURRENT_TRANSACTION)
        }
    }


    /**
     * Тестирование [TransactionsRepositoryImpl.getTransactions]
     */
    @Test
    fun getTransactionsTest() {
        val expectedValue = arrayListOf(CURRENT_TRANSACTION)
        val result = transactionsRepository.getTransactions().blockingGet()
        assertEquals(expectedValue, result)
    }
}