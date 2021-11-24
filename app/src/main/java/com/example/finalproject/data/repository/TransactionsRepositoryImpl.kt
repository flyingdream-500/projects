package com.example.finalproject.data.repository

import com.example.finalproject.data.db.dao.TransactionsDao
import com.example.finalproject.domain.repository.TransactionsRepository
import com.example.finalproject.model.transaction.CurrencyTransaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

/**
 * Конкретная реализация репозитория [TransactionsRepository] по работе с выполненными транзакциями
 * Создан в [com.example.cleararchcurrency.presentation.viewmodel.ViewModelFactory]
 * Параметры:
 * @param transactionsDao - интерфейс для работы с БД
 */
class TransactionsRepositoryImpl(
    private val transactionsDao: TransactionsDao
) : TransactionsRepository {

    //получение выполненных транзакции из БД
    override fun getTransactions(): Maybe<List<CurrencyTransaction>> {
        return transactionsDao.getTransactions()
    }

    //добавление выполненной транзакции в БД
    override fun addTransaction(currencyTransaction: CurrencyTransaction) : Completable {
        return transactionsDao.addTransaction(currencyTransaction)
    }
}
