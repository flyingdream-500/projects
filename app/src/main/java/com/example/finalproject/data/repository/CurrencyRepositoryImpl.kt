package com.example.finalproject.data.repository

import com.example.finalproject.data.db.dao.CurrentCurrencyDao
import com.example.finalproject.data.network.api.CurrencyApi
import com.example.finalproject.data.network.api.OkHttpCurrencyApiImpl
import com.example.finalproject.domain.repository.CurrencyRepository
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.model.network.CurrentCurrency
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single


/**
 * Конкретная реализация репозитория [CurrencyRepository] по работе с получением курсов валют
 * Берем данные из БД, если они актуальны на сегодня, иначе обращаемся к серверу и записываем в БД
 * Параметры:
 * @param currencyApi для вызова метода получения данных, реализован с помощью OkHttp[OkHttpCurrencyApiImpl]
 * @param currentCurrencyDao сохранение и получение данных о курсах валют из БД [CurrentCurrencyDao]
 */

class CurrencyRepositoryImpl(
    private val currencyApi: CurrencyApi,
    private val currentCurrencyDao: CurrentCurrencyDao,
) : CurrencyRepository {

    /**
     * Метод для получения класса с курсами валют из БД
     */
    override fun getCurrentCurrencyItem(): Maybe<CurrentCurrencyItem> {
        return currentCurrencyDao.getCurrentCurrency()
    }

    /**
     * Метод для добавления класса с курсами валют в БД
     * @param currentCurrencyItem - класс с курсами валют
     */
    override fun addCurrentCurrencyItem(currentCurrencyItem: CurrentCurrencyItem): Completable {
        return currentCurrencyDao.addCurrentCurrency(currentCurrencyItem)
    }

    /**
     * Метод для получения данных через интернет из API, реализация в [OkHttpCurrencyApiImpl]
     */
    override fun getRemoteCurrentCurrency(): Single<CurrentCurrency> {
        return currencyApi.getCurrency()
    }
}