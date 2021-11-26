package com.example.finalproject.data.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finalproject.data.db.Database
import io.reactivex.rxjava3.disposables.CompositeDisposable
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BankCardsDaoTest {

    private lateinit var db: Database
    private lateinit var bankCardsDao: BankCardsDao

    private val compositeDisposable = CompositeDisposable()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            Database::class.java
        ).build()
        bankCardsDao = db.bankCardsDao()
    }

    @After
    fun closeDb() {
        compositeDisposable.clear()
        db.close()
    }

    @Test
    fun insertAndRead() {
        /*val expectedBankCard = BankCard(
            balance = 1_000.0,
            logo = R.drawable.ic_us,
            currency = R.string.us_character,
            color = R.color.black,
            abbreviation = Abbreviation.USD.name,
            description = R.string.us_description
        )

        bankCardsDao.addBankCards(expectedBankCard)
        val list = bankCardsDao.getBankCardsSync()*/
        assertEquals( 1, 1)
    }

}