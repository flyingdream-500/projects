package com.example.finalproject.domain.usecase.interfaces

import com.example.finalproject.model.transaction.CurrencyTransaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

interface TransactionInteractor {

    fun getTransactions(): Maybe<List<CurrencyTransaction>>

    fun addTransaction(currencyTransaction: CurrencyTransaction): Completable
}