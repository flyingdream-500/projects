package com.example.finalproject.domain.usecase.interfaces

import com.example.finalproject.data.repository.TransactionsRepositoryImpl
import com.example.finalproject.domain.usecase.TransactionsInteractorImpl
import com.example.finalproject.model.transaction.CurrencyTransaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

/**
 * Интерфейс интерактора по работе с выполненными валютными операциями
 * Реализация в [TransactionsInteractorImpl], используем [TransactionsRepositoryImpl]
 */
interface TransactionInteractor {

    /**
     * Метод для получения списка транзакции из БД
     */
    fun getTransactions(): Maybe<List<CurrencyTransaction>>

    /**
     * Метод для добавления транзакции в БД
     * @param currencyTransaction - класс выполненной валютной транзакции
     */
    fun addTransaction(currencyTransaction: CurrencyTransaction): Completable
}