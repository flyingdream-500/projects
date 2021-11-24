package com.example.finalproject.domain.usecase

import com.example.finalproject.model.transaction.CurrencyTransaction
import com.example.finalproject.domain.repository.TransactionsRepository
import com.example.finalproject.domain.usecase.interfaces.TransactionInteractor
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe


/**
 * UseCases для работы с валютными операциями
 * Параметры:
 * @param transactionsRepository реализован в классе [com.example.cleararchcurrency.data.repository.TransactionsRepositoryImpl]
 * Экземпляр класса передается в [com.example.cleararchcurrency.presentation.viewmodel.SharedViewModel]
 */
class TransactionsInteractorImpl(
    private val transactionsRepository: TransactionsRepository
) : TransactionInteractor {
    override fun getTransactions(): Maybe<List<CurrencyTransaction>> {
        return transactionsRepository.getTransactions()
    }

    override fun addTransaction(currencyTransaction: CurrencyTransaction): Completable {
        return transactionsRepository.addTransaction(currencyTransaction)
    }
}