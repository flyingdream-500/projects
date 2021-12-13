package com.example.finalproject.domain.usecase.interfaces

import com.example.finalproject.data.network.api.OkHttpCurrencyApiImpl
import com.example.finalproject.data.repository.CurrencyRepositoryImpl
import com.example.finalproject.domain.usecase.CurrencyInteractorImpl
import com.example.finalproject.model.currency.CurrentCurrencyItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

/**
 * Интерфейс интерактора по работе с курсами валют
 * Реализация в [CurrencyInteractorImpl], используем [CurrencyRepositoryImpl]
 */
interface CurrencyInteractor  {

     /**
      * Метод для получения класса с курсами валют из БД
      */
     fun getCurrentCurrencyItem(): Maybe<CurrentCurrencyItem>

     /**
      * Метод для добавления класса с курсами валют в БД
      * @param currentCurrencyItem - класс с курсами валют
      */
     fun addCurrentCurrencyItem(currentCurrencyItem: CurrentCurrencyItem): Completable

     /**
      * Метод для получения данных через интернет из API, реализация в [OkHttpCurrencyApiImpl]
      */
     fun getRemoteCurrentCurrency(): Single<CurrentCurrencyItem>

}