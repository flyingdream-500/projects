package com.example.finalproject.data.repository

import com.example.finalproject.data.db.dao.TransactionsDao
import com.example.finalproject.domain.repository.TransactionsRepository
import com.example.finalproject.model.transaction.CurrencyTransaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

/**
 * Конкретная реализация репозитория [TransactionsRepository] по работе с выполненными транзакциями
 * Параметры:
 * @param transactionsDao - интерфейс для работы с БД [TransactionsDao]
 */
class TransactionsRepositoryImpl(
    private val transactionsDao: TransactionsDao
) : TransactionsRepository {

    /**
     * Метод для получения списка транзакции из БД
     */
    override fun getTransactions(): Maybe<List<CurrencyTransaction>> {
        return transactionsDao.getTransactions()
    }

    /**
     * Метод для добавления транзакции в БД
     * @param currencyTransaction - класс выполненной валютной транзакции
     */
    override fun addTransaction(currencyTransaction: CurrencyTransaction) : Completable {
        return transactionsDao.addTransaction(currencyTransaction)
    }
}
