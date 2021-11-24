package com.example.finalproject.data.repository

import com.example.finalproject.data.db.dao.CurrentCurrencyDao
import com.example.finalproject.data.network.api.CurrencyApi
import com.example.finalproject.domain.repository.CurrencyRepository
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.model.network.CurrentCurrency
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single


/**
 * Конкретная реализация репозитория [CurrencyRepository] по работе с получением курсов валют
 * Берем данные из памяти устройства, если они актуальны на сегодня, иначе обращаемся к серверу и записываем в память
 * Создан в [com.example.cleararchcurrency.presentation.viewmodel.ViewModelFactory]
 * Параметры:
 * @param currencyApi для вызова метода получения данных, реализован с помощью OkHttp[com.example.cleararchcurrency.data.network.api.OkHttpCurrencyApiImpl]
 * @param currencyStore сохранение и получение данных о курсах валют с SharedPreferences[com.example.cleararchcurrency.data.store.StoreImpl]
 */

class CurrencyRepositoryImpl(
    private val currencyApi: CurrencyApi,
    private val currentCurrencyDao: CurrentCurrencyDao,
) : CurrencyRepository {

    override fun getFromDb(): Maybe<CurrentCurrencyItem> {
        return currentCurrencyDao.getCurrentCurrency()
    }

    override fun addingToDb(currentCurrencyItem: CurrentCurrencyItem): Completable {
        return currentCurrencyDao.addCurrentCurrency(currentCurrencyItem)
    }

    override fun getRemoteCurrentCurrency(): Single<CurrentCurrency> {
        return currencyApi.getCurrency()
    }
}