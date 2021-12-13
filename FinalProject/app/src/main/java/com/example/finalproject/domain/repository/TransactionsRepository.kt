package com.example.finalproject.domain.repository

import com.example.finalproject.data.db.dao.TransactionsDao
import com.example.finalproject.data.repository.TransactionsRepositoryImpl
import com.example.finalproject.model.transaction.CurrencyTransaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe


/**
 * Интерфейс репозитория по работе с выполненными операциями
 * Реализация в [TransactionsRepositoryImpl], данные получаем из [TransactionsDao]
 */
interface TransactionsRepository {

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