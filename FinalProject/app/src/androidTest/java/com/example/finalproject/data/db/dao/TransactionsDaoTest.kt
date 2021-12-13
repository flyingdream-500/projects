package com.example.finalproject.data.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.finalproject.args.CURRENT_TRANSACTION_DB
import com.example.finalproject.data.db.Database
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Тестирование базы данных [TransactionsDao] по работе с выполненными операциями
 */
class TransactionsDaoTest {

    private lateinit var db: Database
    private lateinit var transactionsDao: TransactionsDao

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            Database::class.java
        ).build()
        transactionsDao = db.transactionsDao()
    }

    /**
     * Данный метод будет вызван после каждого тестового метода.
     */
    @After
    fun closeDb() {
        db.close()
    }

    /**
     * Тестирование [TransactionsDao.addTransaction] и [TransactionsDao.getTransactions]
     */
    @Test
    fun addAndGetTransactionTest() {
        val expectedValue = CURRENT_TRANSACTION_DB
        transactionsDao.addTransaction(CURRENT_TRANSACTION_DB).blockingAwait()
        val result = transactionsDao.getTransactions().blockingGet()
        assertEquals(expectedValue, result!![0])
    }

}