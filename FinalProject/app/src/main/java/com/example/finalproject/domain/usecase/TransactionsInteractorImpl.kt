package com.example.finalproject.domain.usecase

import com.example.finalproject.data.repository.TransactionsRepositoryImpl
import com.example.finalproject.domain.repository.TransactionsRepository
import com.example.finalproject.domain.usecase.interfaces.TransactionInteractor
import com.example.finalproject.model.transaction.CurrencyTransaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe


/**
 * Конкретная реализация интерфейса [TransactionInteractor] для работы с выполненными валютными операциями
 * Параметры:
 * @param transactionsRepository репозиторий по работе с выполненными транзакциями, реализован в классе [TransactionsRepositoryImpl]
 */
class TransactionsInteractorImpl(
    private val transactionsRepository: TransactionsRepository
) : TransactionInteractor {

    /**
     * Метод для получения списка транзакции из БД
     */
    override fun getTransactions(): Maybe<List<CurrencyTransaction>> {
        return transactionsRepository.getTransactions()
    }

    /**
     * Метод для добавления транзакции в БД
     * @param currencyTransaction - класс выполненной валютной транзакции
     */
    override fun addTransaction(currencyTransaction: CurrencyTransaction): Completable {
        return transactionsRepository.addTransaction(currencyTransaction)
    }
}