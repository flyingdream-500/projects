package com.example.finalproject.domain.repository

import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.model.network.CurrentCurrency
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

/**
 * Методы по работе с курсом валют
 * Реализация в [com.example.cleararchcurrency.data.repository.CurrencyRepositoryImpl]
 */
interface CurrencyRepository {

    fun getFromDb(): Maybe<CurrentCurrencyItem>

    fun addingToDb(currentCurrencyItem: CurrentCurrencyItem): Completable

    fun getRemoteCurrentCurrency(): Single<CurrentCurrency>

}