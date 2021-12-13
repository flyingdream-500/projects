package com.example.finalproject.data.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.finalproject.args.CURRENT_CURRENCY_ITEM_DB
import com.example.finalproject.data.db.Database
import com.example.finalproject.data.db.typeconverter.CurrencyTypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Тестирование базы данных [CurrentCurrencyDao] по работе с курсом валют
 */
class CurrentCurrencyDaoTest {

    private lateinit var db: Database
    private lateinit var moshi: Moshi
    private lateinit var currencyDao: CurrentCurrencyDao

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun createDb() {
        moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        CurrencyTypeConverter.initialize(moshi)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            Database::class.java
        )
            .build()
        currencyDao = db.currentCurrencyDao()
    }

    /**
     * Данный метод будет вызван после каждого тестового метода.
     */
    @After
    fun closeDb() {
        db.close()
    }

    /**
     * Тестирование [CurrentCurrencyDao.addCurrentCurrency] и [CurrentCurrencyDao.getCurrentCurrency]
     */
    @Test
    fun addAndGetCurrencyItemTest() {
        val expectedValue = CURRENT_CURRENCY_ITEM_DB
        currencyDao.addCurrentCurrency(CURRENT_CURRENCY_ITEM_DB).blockingAwait()
        val result = currencyDao.getCurrentCurrency().blockingGet()
        assertEquals(expectedValue, result)
    }



}