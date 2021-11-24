package com.example.finalproject.domain.usecase.interfaces

import com.example.finalproject.model.currency.CurrentCurrencyItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface CurrencyInteractor  {
     fun getFromDb(): Maybe<CurrentCurrencyItem>

     fun addingToDb(currentCurrencyItem: CurrentCurrencyItem): Completable

     fun remoteCurrentCurrency(): Single<CurrentCurrencyItem>

}