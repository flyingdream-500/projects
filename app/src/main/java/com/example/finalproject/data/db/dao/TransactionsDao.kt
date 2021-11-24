package com.example.finalproject.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.finalproject.model.transaction.CurrencyTransaction
import com.example.finalproject.utils.constants.DataBaseConstants.TRANSACTIONS_TABLE_NAME
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe


/**
 * Интерфейс с методами взаимодействия с БД
 */
@Dao
interface TransactionsDao {

    @Query("SELECT * FROM $TRANSACTIONS_TABLE_NAME ORDER BY id DESC")
    fun getTransactions(): Maybe<List<CurrencyTransaction>>

    @Insert
    fun addTransaction(currencyTransaction: CurrencyTransaction): Completable

}