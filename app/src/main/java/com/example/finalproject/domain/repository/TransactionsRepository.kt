package com.example.finalproject.domain.repository

import com.example.finalproject.model.transaction.CurrencyTransaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe


/**
 * Методы по работе с выполненными операциями
 * Реализация в [com.example.cleararchcurrency.data.repository.TransactionsRepositoryImpl]
 */
interface TransactionsRepository {

    fun getTransactions(): Maybe<List<CurrencyTransaction>>

    fun addTransaction(currencyTransaction: CurrencyTransaction): Completable

}