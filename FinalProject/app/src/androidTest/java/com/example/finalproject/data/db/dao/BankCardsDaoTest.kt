package com.example.finalproject.data.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finalproject.R
import com.example.finalproject.args.DEFAULT_BANK_CARD_DB
import com.example.finalproject.data.db.Database
import com.example.finalproject.domain.usecase.BankCardsInteractorImpl
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.utils.common.Abbreviation.USD
import io.reactivex.rxjava3.disposables.CompositeDisposable
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.Throws

/**
 * Тестирование базы данных [BankCardsDao] по работе с получением банковских карт
 */
class BankCardsDaoTest {

    private lateinit var db: Database
    private lateinit var bankCardsDao: BankCardsDao


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
        bankCardsDao = db.bankCardsDao()
    }

    /**
     * Данный метод будет вызван после каждого тестового метода.
     */
    @After
    fun closeDb() {
        db.close()
    }


    /**
     * Тестирование [BankCardsDao.addAndGetCards]
     */
    @Test
    fun addAndGetCardsTest() {
        val expectedValue = DEFAULT_BANK_CARD_DB
        val list = bankCardsDao.addAndGetCards(DEFAULT_BANK_CARD_DB).blockingGet()
        assertEquals( expectedValue, list[0])
    }

    /**
     * Тестирование [BankCardsDao.getBankCardByAbbr] & [BankCardsDao.addBankCards]
     */
    @Test
    fun addAndGetByAttrTest() {
        val expectedValue = DEFAULT_BANK_CARD_DB
        bankCardsDao.addBankCards(DEFAULT_BANK_CARD_DB).blockingAwait()
        val result = bankCardsDao.getBankCardByAbbr(DEFAULT_BANK_CARD_DB.abbreviation).blockingGet()
        assertEquals( expectedValue, result)
    }

}